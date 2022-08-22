package dev.mccue.log.alpha.cloudwatch;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.publisher.Publisher;

import java.util.List;

public record CloudwatchConfig(Log.Level minimumLevel) implements Publisher {
    @Override
    public void publish(List<Log> logs) {
        System.out.println("[" + this + "]: " + logs);
    }
}
