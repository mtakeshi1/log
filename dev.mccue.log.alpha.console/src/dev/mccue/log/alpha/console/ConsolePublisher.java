package dev.mccue.log.alpha.console;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.publisher.Publisher;

import java.util.ArrayList;
import java.util.List;

public final class ConsolePublisher implements Publisher {
    @Override
    public void publish(List<dev.mccue.log.alpha.Log> logs) {
        for (var log : logs) {
            synchronized (System.out) {
                System.out.print("[");
                System.out.print(switch (log.level()) {
                    case UNSPECIFIED -> "?";
                    default -> log.level().toString();
                });
                System.out.print(" ");
                System.out.print(log.thread());
                System.out.print("]: ");
                System.out.print(log.category().namespace());
                System.out.print("/");
                System.out.print(log.category().name());
                System.out.print(" ");
                var entries = new ArrayList<>(log.entries());
                var ctx = log.context();
                while (ctx instanceof Log.Context.NotEmpty someCtx) {
                    entries.addAll(someCtx.entries());
                    ctx = someCtx.parent();
                }
                System.out.println(entries);
            }
        }
    }
}
