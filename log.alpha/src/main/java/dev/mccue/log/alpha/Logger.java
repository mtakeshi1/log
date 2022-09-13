package dev.mccue.log.alpha;

import java.util.List;

/**
 * A logger.
 *
 * @implNote
 * <p>Default methods are provided for common log levels and overloads are provided
 * for up to 10 Log.Entry parameters to avoid allocating arrays while having
 * convenient calling conventions.</p>
 */
@FunctionalInterface
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

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9,
            Log.Entry entry10
    ) {
        log(level, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9
    ) {
        log(level, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8
    ) {
        log(level, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7
    ) {
        log(level, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6
    ) {
        log(level, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5
    ) {
        log(level, category, List.of(entry1, entry2, entry3, entry4, entry5));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4
    ) {
        log(level, category, List.of(entry1, entry2, entry3, entry4));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3
    ) {
        log(level, category, List.of(entry1, entry2, entry3));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2
    ) {
        log(level, category, List.of(entry1, entry2));
    }

    default void log(
            Log.Level level,
            Log.Category category,
            Log.Entry entry1
    ) {
        log(level, category, List.of(entry1));
    }

    default void log(Log.Level level, Log.Category category, Log.Entry... logEntries) {
        log(level, category, List.of(logEntries));
    }

    default void trace(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.TRACE, category, logEntries);
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.TRACE, category, logEntries);
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9,
            Log.Entry entry10
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2, entry3, entry4, entry5));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2, entry3, entry4));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2, entry3));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2
    ) {
        log(Log.Level.TRACE, category, List.of(entry1, entry2));
    }

    /**
     * @see Logger#trace(Log.Category, List)
     */
    default void trace(
            Log.Category category,
            Log.Entry entry1
    ) {
        log(Log.Level.TRACE, category, List.of(entry1));
    }

    default void debug(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.DEBUG, category, logEntries);
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.DEBUG, category, logEntries);
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9,
            Log.Entry entry10
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2, entry3, entry4, entry5));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2, entry3, entry4));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2, entry3));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1, entry2));
    }

    /**
     * @see Logger#debug(Log.Category, List)
     */
    default void debug(
            Log.Category category,
            Log.Entry entry1
    ) {
        log(Log.Level.DEBUG, category, List.of(entry1));
    }

    default void info(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.INFO, category, logEntries);
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.INFO, category, logEntries);
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9,
            Log.Entry entry10
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2, entry3, entry4, entry5));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2, entry3, entry4));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2, entry3));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2
    ) {
        log(Log.Level.INFO, category, List.of(entry1, entry2));
    }

    /**
     * @see Logger#info(Log.Category, List)
     */
    default void info(
            Log.Category category,
            Log.Entry entry1
    ) {
        log(Log.Level.INFO, category, List.of(entry1));
    }

    default void warn(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.WARN, category, logEntries);
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.WARN, category, logEntries);
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9,
            Log.Entry entry10
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2, entry3, entry4, entry5));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2, entry3, entry4));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2, entry3));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2
    ) {
        log(Log.Level.WARN, category, List.of(entry1, entry2));
    }

    /**
     * @see Logger#warn(Log.Category, List)
     */
    default void warn(
            Log.Category category,
            Log.Entry entry1
    ) {
        log(Log.Level.WARN, category, List.of(entry1));
    }

    default void error(Log.Category category, List<Log.Entry> logEntries) {
        log(Log.Level.ERROR, category, logEntries);
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(Log.Category category, Log.Entry... logEntries) {
        log(Log.Level.ERROR, category, logEntries);
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9,
            Log.Entry entry10
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8,
            Log.Entry entry9
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7,
            Log.Entry entry8
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6,
            Log.Entry entry7
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5,
            Log.Entry entry6
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4,
            Log.Entry entry5
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2, entry3, entry4, entry5));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3,
            Log.Entry entry4
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2, entry3, entry4));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2,
            Log.Entry entry3
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2, entry3));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1,
            Log.Entry entry2
    ) {
        log(Log.Level.ERROR, category, List.of(entry1, entry2));
    }

    /**
     * @see Logger#error(Log.Category, List)
     */
    default void error(
            Log.Category category,
            Log.Entry entry1
    ) {
        log(Log.Level.ERROR, category, List.of(entry1));
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
    sealed interface Namespaced {
        /**
         * Logs an event.
         *
         * @param level The level to log at.
         * @param name The name of the event. This should *not* be a sentence, but instead a unique identifier
         *             for the event within the namespace of the logger.
         * @param entries The entries to attach to the log.
         */
        void log(Log.Level level, String name, List<Log.Entry> entries);

        default void log(Log.Level level, String name, Log.Entry... entries) {
            log(level, name, List.of(entries));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9,
                Log.Entry entry10
        ) {
            log(level, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9
        ) {
            log(level, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8
        ) {
            log(level, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7
        ) {
            log(level, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6
        ) {
            log(level, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5
        ) {
            log(level, name, List.of(entry1, entry2, entry3, entry4, entry5));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4
        ) {
            log(level, name, List.of(entry1, entry2, entry3, entry4));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3
        ) {
            log(level, name, List.of(entry1, entry2, entry3));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1,
                Log.Entry entry2
        ) {
            log(level, name, List.of(entry1, entry2));
        }

        default void log(
                Log.Level level,
                String name,
                Log.Entry entry1
        ) {
            log(level, name, List.of(entry1));
        }

        default void trace(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.TRACE, eventType, logEntries);
        }

        /**
         * Logs an event at the "trace" level.
         *
         * @param eventType The type of the event. This should *not* be english, but instead a unique identifier
         *                  for the event within the namespace of the logger.
         * @param logEntries The entries to attach to the log.
         */
        default void trace(String eventType, Log.Entry... logEntries) {
            log(Log.Level.TRACE, eventType, logEntries);
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9,
                Log.Entry entry10
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2, entry3, entry4, entry5));
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2, entry3, entry4));
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2, entry3));
        }

        default void trace(
                String name,
                Log.Entry entry1,
                Log.Entry entry2
        ) {
            log(Log.Level.TRACE, name, List.of(entry1, entry2));
        }

        default void trace(
                String name,
                Log.Entry entry1
        ) {
            log(Log.Level.TRACE, name, List.of(entry1));
        }

        /**
         * Logs an event at the "debug" level.
         *
         * @param eventType The type of the event. This should *not* be english, but instead a unique identifier
         *                  for the event within the namespace of the logger.
         * @param logEntries The entries to attach to the log.
         */
        default void debug(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.DEBUG, eventType, logEntries);
        }

        default void debug(String eventType, Log.Entry... logEntries) {
            log(Log.Level.DEBUG, eventType, logEntries);
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9,
                Log.Entry entry10
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2, entry3, entry4, entry5));
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2, entry3, entry4));
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2, entry3));
        }

        default void debug(
                String name,
                Log.Entry entry1,
                Log.Entry entry2
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1, entry2));
        }

        default void debug(
                String name,
                Log.Entry entry1
        ) {
            log(Log.Level.DEBUG, name, List.of(entry1));
        }

        /**
         * Logs an event at the "info" level.
         *
         * @param name The type of the event. This should *not* be english, but instead a unique identifier
         *                  for the event within the namespace of the logger.
         * @param entries The entries to attach to the log.
         */
        default void info(String name, List<Log.Entry> entries) {
            log(Log.Level.INFO, name, entries);
        }

        default void info(String name, Log.Entry... entries) {
            log(Log.Level.INFO, name, entries);
        }
        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9,
                Log.Entry entry10
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
        }

        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
        }

        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
        }

        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
        }

        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
        }

        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2, entry3, entry4, entry5));
        }

        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2, entry3, entry4));
        }

        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2, entry3));
        }

        default void info(
                String name,
                Log.Entry entry1,
                Log.Entry entry2
        ) {
            log(Log.Level.INFO, name, List.of(entry1, entry2));
        }

        default void info(
                String name,
                Log.Entry entry1
        ) {
            log(Log.Level.INFO, name, List.of(entry1));
        }

        /**
         * Logs an event at the "warn" level.
         *
         * @param eventType The type of the event. This should *not* be english, but instead a unique identifier
         *                  for the event within the namespace of the logger.
         * @param logEntries The entries to attach to the log.
         */
        default void warn(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.WARN, eventType, logEntries);
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(String eventType, Log.Entry... logEntries) {
            log(Log.Level.WARN, eventType, logEntries);
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9,
                Log.Entry entry10
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2, entry3, entry4, entry5));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2, entry3, entry4));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2, entry3));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1,
                Log.Entry entry2
        ) {
            log(Log.Level.WARN, name, List.of(entry1, entry2));
        }

        /**
         * @see Logger.Namespaced#warn(Log.Category, List)
         */
        default void warn(
                String name,
                Log.Entry entry1
        ) {
            log(Log.Level.WARN, name, List.of(entry1));
        }

        default void error(String eventType, List<Log.Entry> logEntries) {
            log(Log.Level.ERROR, eventType, logEntries);
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(String eventType, Log.Entry... logEntries) {
            log(Log.Level.ERROR, eventType, logEntries);
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9,
                Log.Entry entry10
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9, entry10));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8,
                Log.Entry entry9
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8, entry9));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7,
                Log.Entry entry8
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7, entry8));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6,
                Log.Entry entry7
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6, entry7));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5,
                Log.Entry entry6
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2, entry3, entry4, entry5, entry6));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4,
                Log.Entry entry5
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2, entry3, entry4, entry5));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3,
                Log.Entry entry4
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2, entry3, entry4));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2,
                Log.Entry entry3
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2, entry3));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1,
                Log.Entry entry2
        ) {
            log(Log.Level.ERROR, name, List.of(entry1, entry2));
        }

        /**
         * @see Logger.Namespaced#error(Log.Category, List)
         */
        default void error(
                String name,
                Log.Entry entry1
        ) {
            log(Log.Level.ERROR, name, List.of(entry1));
        }
    }
}

record NamespacedLogger(String namespace, Logger logger) implements Logger.Namespaced {
    @Override
    public void log(Log.Level level, String name, List<Log.Entry> entries) {
        logger.log(level, new Log.Category(namespace, name), entries);
    }
}
