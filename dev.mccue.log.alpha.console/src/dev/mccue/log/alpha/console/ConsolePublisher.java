package dev.mccue.log.alpha.console;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.publisher.Publisher;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ConsolePublisher implements Publisher {
    private void printValue(Log.Entry.Value value) {
        Function<String, String> jsonify = s -> "\"" + s
                .replace("\n", "\\n")
                .replace("\"", "\\\"") + "\"";

        if (value instanceof Log.Entry.Value.String string) {
            System.out.print(jsonify.apply(string.value()));
        }
        else if (value instanceof Log.Entry.Value.Integer i) {
            System.out.print(i.value());
        }
        else if (value instanceof Log.Entry.Value.Throwable throwable){
            var sw = new StringWriter();
            var pw = new PrintWriter(sw);
            throwable.value().printStackTrace(pw);
            System.out.print(jsonify.apply(sw.toString()));
        }
        else if (value instanceof Log.Entry.Value.Null __){
            System.out.print("null");
        }
        else if (value instanceof Log.Entry.Value.Lazy lazy){
            var value2 = lazy.value();
            printValue(value2);
        }
        else {
            System.out.print(jsonify.apply(value.toString()));
        }
    }
    @Override
    public void publish(List<dev.mccue.log.alpha.Log> logs) {
        Function<String, String> jsonify = s -> "\"" + s
                .replace("\n", "\\n")
                .replace("\"", "\\\"") + "\"";

        for (var log : logs) {
            System.out.println(log);
            synchronized (System.out) {
                /*System.out.print("[");
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
                System.out.println(entries);*/

                System.out.print("{");
                System.out.print("\"log/level\":");
                System.out.print(switch (log.level()) {
                    case UNSPECIFIED -> "\"unspecified\"";
                    default -> jsonify.apply(log.level().toString());
                });
                System.out.print(",\"log/thread\":");
                System.out.print(jsonify.apply(log.thread().getName()));
                System.out.print(",\"log/namespace\":");
                System.out.print(jsonify.apply(log.category().namespace()));
                System.out.print(",\"log/name\":");
                System.out.print(jsonify.apply(log.category().name()));

                System.out.print(",\"log/timestamp\":");
                System.out.print(log.timestamp().toEpochMilli());
                System.out.print(",\"log/flake\":");
                System.out.print(jsonify.apply(log.flake().toString()));
                System.out.print(",");
                var entries = new ArrayList<Log.Entry>();
                log.forEach(entries::add);

                var entriesIter = entries.iterator();
                while (entriesIter.hasNext()) {
                    var entry = entriesIter.next();
                    System.out.print(jsonify.apply(entry.key()));
                    System.out.print(":");
                    printValue(entry.value());
                    if (entriesIter.hasNext()) {
                        System.out.print(",");
                    }
                }

                System.out.println("}");

            }
        }
    }
}
