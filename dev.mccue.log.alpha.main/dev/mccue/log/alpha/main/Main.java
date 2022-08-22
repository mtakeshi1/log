package dev.mccue.log.alpha.main;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.console.ConsolePublisher;
import dev.mccue.log.alpha.generate.DeriveLogger;
import dev.mccue.log.alpha.publisher.GlobalFanOutLogger;
import dev.mccue.log.alpha.record.LoggableRecord;
import dev.mccue.log.alpha.sentry.SentryConfig;
import dev.mccue.log.alpha.sentry.SentryPublisher;


@LoggableRecord(defaultLogLevel = Log.Level.INFO)
record ConnectionInfo(int port, String host) implements ConnectionInfoLoggable {}

@DeriveLogger
public final class Main implements MainLog {

    static void doThing() {
        var connectionInfo = new ConnectionInfo(8000, "apple");
        connectionInfo.log(log, "starting-server");
    }

    public static void main(String[] args) throws InterruptedException {
        GlobalFanOutLogger.registerPublisher(new ConsolePublisher());
        GlobalFanOutLogger.registerPublisher(new SentryPublisher(
                new SentryConfig("dsn")
        ));
        int x = 0;
        while (x < 10) {
            Log.inContext(Main::doThing, Log.Entry.of("x", x), Log.Entry.of("exception", new RuntimeException("" + x)));
            x++;
            Thread.sleep(1000);
        }
    }
}
