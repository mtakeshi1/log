package dev.mccue.log.alpha.main;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.LoggerFactory;
import dev.mccue.log.alpha.generate.DeriveLogger;

import java.lang.invoke.MethodHandles;


// @DeriveLogger
public final class Main /* implements MainLog */ {
    private static final Logger.Namespaced log =
            LoggerFactory.getLogger(MethodHandles.lookup());

    private static void f() {
        log.info("starting-dup", Log.Entry.of("port", 8000), Log.Entry.of("name", "something"));
    }
    public static void main(String[] args) {
        Log.inContext(Main::f, Log.Entry.of("abc", "def"));
    }
}
