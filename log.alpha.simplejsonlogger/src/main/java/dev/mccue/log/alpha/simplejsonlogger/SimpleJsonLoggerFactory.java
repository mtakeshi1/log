package dev.mccue.log.alpha.simplejsonlogger;

import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;

public final class SimpleJsonLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger() {
        return new SimpleJsonLogger();
    }
}
