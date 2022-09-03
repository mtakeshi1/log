package dev.mccue.log.alpha.cloudwatch;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.publisher.LogBuffer;
import dev.mccue.log.alpha.publisher.Publisher;

import java.util.List;

public record CloudwatchConfig(Log.Level minimumLevel) implements Publisher {
    @Override
    public LogBuffer inbox() {
        return null;
    }

    @Override
    public void publish(List<Log> logs) {

    }


}
