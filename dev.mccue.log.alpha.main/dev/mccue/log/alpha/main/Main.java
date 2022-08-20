package dev.mccue.log.alpha.main;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Main {
    private static final Logger.Namespaced log =
            LoggerFactory.getLogger(MethodHandles.lookup());

    private static void f() {
        log.info("starting-up", Log.Entry.of("port", 8000), Log.Entry.of("name", "something"));
    }
    public static void main(String[] args) {
        Log.inContext(Main::f, Log.Entry.of("abc", "def"));
    }
}
