package dev.mccue.log.alpha.main;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;
import dev.mccue.log.alpha.generate.DeriveLogger;

import java.util.List;
import java.util.UUID;

@DeriveLogger
public final class Main {
    private static final Logger.Namespaced log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws InterruptedException {
        log.info("some-event", Log.Entry.of("thing", "ABC"));

        Log.setGlobalContext(List.of(
                Log.Entry.of("os.arch", System.getProperty("os.arch")),
                Log.Entry.of("os.name", System.getProperty("os.name")),
                Log.Entry.of("os.version", System.getProperty("os.version")),
                Log.Entry.of("java.version", System.getProperty("java.version"))
        ));

        Log.withContext(
                List.of(Log.Entry.of("trace-id", UUID.randomUUID())),
                () -> {
                    log.info("wow", Log.Entry.of("e", new RuntimeException()));
                    log.info("thing-happened", Log.Entry.of("apple", "man"));
                });
    }
}

