package dev.mccue.log.alpha.main;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;

import java.lang.invoke.MethodHandles;


public final class Main {
    private static final Logger.Namespaced log =
            LoggerFactory.getLogger(MethodHandles.lookup());

    static void doThing(int id) {}

    public static void main(String[] args) {
        int id = 123;
        log.info("starting-process");
        try {
            doThing(id);
        } catch (Exception e) {
            log.error(
                    "failed-this-particular-process",
                    Log.Entry.of("exception", e),
                    Log.Entry.of("id", id)
            );
        }
    }
}
