package dev.mccue.log.alpha.publisher;

import dev.mccue.log.alpha.Log;
import io.vavr.collection.Vector;

import java.util.List;

public interface LogBuffer {
    int size();
    LogBuffer enqueue(Log log);
    LogBuffer dequeue(long offset);
    LogBuffer clear();

    List<OffsetLogPair> items();

    record OffsetLogPair(long offset, Log log) {}

    static LogBuffer create(int capacity) {
       return LogBufferImpl.create(capacity);
    }
}
