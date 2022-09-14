package dev.mccue.log.alpha.cloudwatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.mccue.log.alpha.Flake;
import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.jackson.LogSerializer;
import dev.mccue.log.alpha.publisher.LogBuffer;
import dev.mccue.log.alpha.publisher.Publisher;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;
import software.amazon.awssdk.services.cloudwatchlogs.model.InvalidSequenceTokenException;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsResponse;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public final class CloudwatchPublisher implements Publisher {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        var module = new SimpleModule();
        module.addSerializer(Log.class, new LogSerializer());
        OBJECT_MAPPER.registerModule(module);
    }

    private final CloudWatchLogsClient cloudWatchLogsClient;
    private final AtomicReference<String> nextToken;
    private final String logGroupName;
    private final String logStreamName;
    private final Options options;

    public CloudwatchPublisher(
            CloudWatchLogsClient cloudWatchLogsClient,
            String logGroupName,
            Options options
    ) {
        this.cloudWatchLogsClient = cloudWatchLogsClient;
        this.logGroupName = logGroupName;
        this.logStreamName = Flake.create().toString();
        this.options = options;
        this.nextToken = new AtomicReference<>();
    }

    public CloudwatchPublisher(
            CloudWatchLogsClient cloudWatchLogsClient,
            String logGroupName
    ) {
        this(cloudWatchLogsClient, logGroupName, Options.DEFAULT);
    }

    @Override
    public LogBuffer publish(LogBuffer logs) {
        var inputLogEvents = logs.items().stream()
                .limit(this.options.maxItems)
                .map(event ->
                        {
                            try {
                                return InputLogEvent.builder()
                                        .timestamp(switch (event.log().occurrence()) {
                                            case Log.Occurrence.PointInTime pointInTime ->
                                                    pointInTime.happenedAt().toEpochMilli();
                                            case Log.Occurrence.SpanOfTime spanOfTime ->
                                                    spanOfTime.startedAt().toEpochMilli();
                                        })
                                        .message(OBJECT_MAPPER.writeValueAsString(event.log()))
                                        .build();
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .sorted(Comparator.comparing(InputLogEvent::timestamp))
                .toList();

        Long lastOffset = logs.items().stream().skip(options.maxItems - 1).findFirst()
                .map(LogBuffer.OffsetLogPair::offset)
                .orElse(null);

        LogBuffer remainingLogs;
        if (lastOffset == null) {
            remainingLogs = logs.clear();
        }
        else {
            remainingLogs = logs.dequeue(lastOffset);
        }


        var sequenceToken = this.nextToken.get();
        var putLogEventsRequest = PutLogEventsRequest.builder()
                .logEvents(inputLogEvents)
                .logGroupName(this.logGroupName)
                .logStreamName(this.logStreamName)
                .sequenceToken(sequenceToken)
                .build();

        PutLogEventsResponse response;
        try {
            response = this.cloudWatchLogsClient.putLogEvents(putLogEventsRequest);
            this.nextToken.set(response.nextSequenceToken());
        } catch (InvalidSequenceTokenException invalidSequenceTokenException) {
            this.nextToken.set(invalidSequenceTokenException.expectedSequenceToken());
        }

        return remainingLogs;
    }

    public record Options(
            int maxItems,
            int publishDelay,
            Function<Log, String> logToJsonString
    ) {
        public static final Options DEFAULT = new Options(5000, 1000, log -> {
            try {
                return OBJECT_MAPPER.writeValueAsString(log);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        public Options withMaxItems(int maxItems) {
            return new Options(maxItems, this.publishDelay, this.logToJsonString);
        }

        public Options withPublishDelay(int publishDelay) {
            return new Options(this.maxItems, publishDelay, this.logToJsonString);
        }

        public Options withLogToJsonString(Function<Log, String> logToJsonString) {
            return new Options(this.maxItems, this.publishDelay, logToJsonString);
        }
    }
}
