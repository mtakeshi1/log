package dev.mccue.log.alpha;

import java.util.List;

/**
 * A logger.
 */
public interface Logger {
    /**
     * Logs the log.
     *
     * @param log The log to log.
     */
    void log(Log log);

    default void log(
            Log.Level level,
            Log.Category category,
            List<Log.Entry> logEntries
    ) {
        log(new Log(level, category, logEntries));
    }


    default void log(Log.Level level, Log.Category category, Log.Entry... logEntries) {
        log(level, category, List.of(logEntries));
    }

    default void log(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.UNSPECIFIED, category, logEntries);
    }

    default void log(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.UNSPECIFIED, category, logEntries);
    }

    default void trace(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.TRACE, category, logEntries);
    }

    default void trace(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.TRACE, category, logEntries);
    }

    default void debug(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.DEBUG, category, logEntries);
    }

    default void debug(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.DEBUG, category, logEntries);
    }

    default void info(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.INFO, category, logEntries);
    }

    default void info(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.INFO, category, logEntries);
    }

    default void warn(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.WARN, category, logEntries);
    }

    default void warn(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.WARN, category, logEntries);
    }

    default void error(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.ERROR, category, logEntries);
    }

    default void error(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.ERROR, category, logEntries);
    }

    /**
     * @param namespace The namespace for log categories.
     * @return A namespaced logger wrapping this one.
     */
    default Namespaced namespaced(String namespace) {
        return new NamespacedLogger(namespace, this);
    }

    /**
     * A logger with the namespace of its category already filled in.
     *
     * <p>The most common use of this is to have a logger for a particular class.</p>
     */
    interface Namespaced {
        void log(Log.Level level, String name, List<Log.Entry> logEntries);

        default void log(Log.Level level, String name, Log.Entry... logEntries) {
            log(level, name, List.of(logEntries));
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
    public void log(Log.Level level, String name, List<Log.Entry> logEntries) {
        logger.log(level, new Log.Category(namespace, name), logEntries);
    }
}
