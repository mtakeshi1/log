package dev.mccue.log.alpha.publisher;

import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;

import java.util.ServiceLoader;

public final class FanOutLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger() {
        return new FanOutLogger(ServiceLoader
                .load(Publisher.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList());
    }
}
