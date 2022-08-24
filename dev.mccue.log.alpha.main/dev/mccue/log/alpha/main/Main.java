package dev.mccue.log.alpha.main;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;
import dev.mccue.log.alpha.console.ConsolePublisher;
import dev.mccue.log.alpha.publisher.GlobalFanOutLogger;
import dev.mccue.log.alpha.sentry.SentryConfig;
import dev.mccue.log.alpha.sentry.SentryPublisher;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.UUID;

public final class Main  {
    private static final Logger.Namespaced log =
            LoggerFactory.getLogger(Main.class);
    private static final org.slf4j.Logger slf4jLog = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        GlobalFanOutLogger.registerPublisher(new ConsolePublisher());
        GlobalFanOutLogger.registerPublisher(new SentryPublisher(
                new SentryConfig("dsn")
        ));

        slf4jLog.info("Something happened {}", 123);

        Thread.sleep(1000);
    }
}
