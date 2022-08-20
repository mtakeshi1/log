package dev.mccue.log.alpha.publisher;

import dev.mccue.log.alpha.Log;

import java.util.List;

public interface Publisher {
    void publish(List<Log> logs);
}
