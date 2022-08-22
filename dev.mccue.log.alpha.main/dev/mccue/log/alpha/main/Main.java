package dev.mccue.log.alpha.main;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;
import dev.mccue.log.alpha.console.ConsolePublisher;
import dev.mccue.log.alpha.publisher.GlobalFanOutLogger;

import java.lang.invoke.MethodHandles;


public final class Main {
    private static final Logger.Namespaced log =
            LoggerFactory.getLogger(MethodHandles.lookup());

    static void doThing() {
        log.info("starting", Log.Entry.of("whatever", 123));
    }

    public static void main(String[] args) throws InterruptedException {
        GlobalFanOutLogger.registerPublisher(new ConsolePublisher());
        int x = 0;
        while (x < 10) {
            Log.inContext(Main::doThing, Log.Entry.of("x", x));
            x++;
            Thread.sleep(1000);
        }
    }
}
