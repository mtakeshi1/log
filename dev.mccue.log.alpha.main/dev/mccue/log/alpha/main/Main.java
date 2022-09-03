package dev.mccue.log.alpha.main;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;
import dev.mccue.log.alpha.console.ConsolePublisher;
import dev.mccue.log.alpha.publisher.GlobalFanOutLogger;
import dev.mccue.log.alpha.record.LoggableRecord;
import dev.mccue.log.alpha.sentry.SentryConfig;
import dev.mccue.log.alpha.sentry.SentryPublisher;

import java.util.List;

public final class Main {
    private static final Logger.Namespaced log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        GlobalFanOutLogger.registerPublisher(new ConsolePublisher());
        GlobalFanOutLogger.registerPublisher(new SentryPublisher(
                new SentryConfig("dsn")
        ));

        // package.Main/something-happened
        var re = new RuntimeException();
        log.info("something-happened", Log.Entry.of("request-id", "abc"), Log.Entry.of("exception", re));

        Thread.sleep(1000);
    }
}
