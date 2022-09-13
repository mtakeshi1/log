package dev.mccue.log.alpha.cloudwatch;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.publisher.LogBuffer;
import dev.mccue.log.alpha.publisher.Publisher;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public final class CloudwatchPublisher implements Publisher {
    private static final System.Logger LOG =
            System.getLogger("dev.mccue.log.alpha.cloudwatch.CloudwatchPublisher");
    private final Consumer<Log> todo;
    private final CloudWatchClient cloudWatchClient;

    public CloudwatchPublisher(CloudWatchClient cloudWatchClient) {
        this.todo = (__) -> {};
        this.cloudWatchClient = cloudWatchClient;
    }

    public CloudwatchPublisher(Consumer<Log> todo) {
        this.todo = todo;
        this.cloudWatchClient = null;
    }

    @Override
    public LogBuffer publish(LogBuffer logs) {
        return null;
    }
}
