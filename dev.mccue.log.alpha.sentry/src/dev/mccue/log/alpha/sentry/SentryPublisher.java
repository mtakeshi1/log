package dev.mccue.log.alpha.sentry;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.publisher.Publisher;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public final class SentryPublisher implements Publisher {
    public SentryPublisher(SentryConfig config) {

    }

    @Override
    public void publish(List<dev.mccue.log.alpha.Log> logs) {
        for (var log : logs) {
            if (log.level() == Log.Level.WARN || log.level() == Log.Level.ERROR || log.level() == Log.Level.UNSPECIFIED) {
                var throwables = new ArrayList<Throwable>();
                var values = new ArrayDeque<>(log.entries().stream().map(Log.Entry::value).toList());
                var ctx = log.context();
                while (ctx instanceof Log.Context.Child childCtx) {
                    values.addAll(childCtx.entries().stream().map(Log.Entry::value).toList());
                    ctx = childCtx.parent();
                }
                while (!values.isEmpty()) {
                    var value = values.pop();
                    if (value instanceof Log.Entry.Value.Throwable throwableLogValue) {
                        throwables.add(throwableLogValue.value());
                    }
                    else if (value instanceof Log.Entry.Value.List listLogValue) {
                        values.addAll(listLogValue.value());
                    }
                    else if (value instanceof Log.Entry.Value.Set setLogValue) {
                        values.addAll(setLogValue.value());
                    }
                }

                if (!throwables.isEmpty()) {
                    System.out.println("[SENTRY]: " + throwables);
                }
            }
        }

    }
}
