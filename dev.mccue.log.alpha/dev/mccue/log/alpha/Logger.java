package dev.mccue.log.alpha;

import java.time.Instant;
import java.util.List;

public interface Logger {
    void log(Log log);

    default void log(
            Instant timestamp,
            Flake flake,
            Log.Level level,
            Log.Type type,
            List<Log.Entry> logEntries
    ) {
        log(new Log(
                timestamp,
                flake,
                level,
                type,
                logEntries
        ));
    }

    default void log(Log.Level level, Log.Type type, List<Log.Entry> logEntries) {
        log(Instant.now(), Flake.create(), level, type, logEntries);
    }

    default void log(Log.Level level, Log.Type type, Log.Entry... logEntries) {
        log(Instant.now(), Flake.create(), level, type, List.of(logEntries));
    }

    default void log(Log.Type type, Log.Entry... logEntries) {
        log(Log.Level.UNSPECIFIED, type, logEntries);
    }

    default void log(Log.Type type, List<Log.Entry> logEntries) {
        log(Log.Level.UNSPECIFIED, type, logEntries);
    }

    default void trace(Log.Type type, Log.Entry... logEntries) {
        log(Log.Level.TRACE, type, logEntries);
    }

    default void trace(Log.Type type, List<Log.Entry> logEntries) {
        log(Log.Level.TRACE, type, logEntries);
    }

    default void debug(Log.Type type, Log.Entry... logEntries) {
        log(Log.Level.DEBUG, type, logEntries);
    }

    default void debug(Log.Type type, List<Log.Entry> logEntries) {
        log(Log.Level.DEBUG, type, logEntries);
    }

    default void info(Log.Type type, Log.Entry... logEntries) {
        log(Log.Level.INFO, type, logEntries);
    }

    default void info(Log.Type type, List<Log.Entry> logEntries) {
        log(Log.Level.INFO, type, logEntries);
    }

    default void warn(Log.Type type, Log.Entry... logEntries) {
        log(Log.Level.WARN, type, logEntries);
    }

    default void warn(Log.Type type, List<Log.Entry> logEntries) {
        log(Log.Level.WARN, type, logEntries);
    }

    default void error(Log.Type type, Log.Entry... logEntries) {
        log(Log.Level.ERROR, type, logEntries);
    }

    default void error(Log.Type type, List<Log.Entry> logEntries) {
        log(Log.Level.ERROR, type, logEntries);
    }

    default Namespaced namespaced(String namespace) {
        return new NamespacedLogger(namespace, this);
    }

    interface Namespaced {
        void log(Instant timestamp, Flake flake, Log.Level level, String name, List<Log.Entry> logEntries);

        default void log(Log.Level level, String name, List<Log.Entry> logEntries) {
            log(Instant.now(), Flake.create(), level, name, logEntries);
        }

        default void log(Log.Level level, String name, Log.Entry... logEntries) {
            log(Instant.now(), Flake.create(), level, name, List.of(logEntries));
        }

        default void log(String name, Log.Entry... logEntries) {
            log(Log.Level.UNSPECIFIED, name, logEntries);
        }

        default void log(String name, List<Log.Entry> logEntries) {
            log(Log.Level.UNSPECIFIED, name, logEntries);
        }

        default void trace(String eventType, Log.Entry... logEntries) {
            log(Log.Level.TRACE, eventType, logEntries);
        }

        default void trace(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.TRACE, eventType, logEntries);
        }

        default void debug(String eventType, Log.Entry... logEntries) {
            log(Log.Level.DEBUG, eventType, logEntries);
        }

        default void debug(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.DEBUG, eventType, logEntries);
        }

        default void info(String eventType, Log.Entry... logEntries) {
            log(Log.Level.INFO, eventType, logEntries);
        }

        default void info(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.INFO, eventType, logEntries);
        }

        default void warn(String eventType, Log.Entry... logEntries) {
            log(Log.Level.WARN, eventType, logEntries);
        }

        default void warn(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.WARN, eventType, logEntries);
        }

        default void error(String eventType, Log.Entry... logEntries) {
            log(Log.Level.ERROR, eventType, logEntries);
        }

        default void error(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.ERROR, eventType, logEntries);
        }
    }
}

record NamespacedLogger(String namespace, Logger logger) implements Logger.Namespaced {
    @Override
    public void log(Instant timestamp, Flake flake, Log.Level level, String name, List<Log.Entry> logEntries) {
        logger.log(timestamp, flake, level, new Log.Type(namespace, name), logEntries);
    }
}
