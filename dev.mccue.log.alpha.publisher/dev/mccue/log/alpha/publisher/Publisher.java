package dev.mccue.log.alpha.publisher;

import dev.mccue.log.alpha.Log;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface Publisher {
    default LogBuffer inbox() {
        return null;
    }
    void publish(List<Log> logs);

    default Optional<Duration> publishDelay() {
        return Optional.of(Duration.ofMillis(200));
    }
}
