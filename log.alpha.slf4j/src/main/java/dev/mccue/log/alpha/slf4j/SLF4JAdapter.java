package dev.mccue.log.alpha.slf4j;

import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.LoggerFactory;
import org.slf4j.*;
import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

import java.net.URI;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public final class SLF4JAdapter implements SLF4JServiceProvider {

    @Override
    public ILoggerFactory getLoggerFactory() {
        return SLF4JLogger::new;
    }

    @Override
    public IMarkerFactory getMarkerFactory() {
        return new BasicMarkerFactory();
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        return new BasicMDCAdapter();
    }

    @Override
    public String getRequestedApiVersion() {
        return "2.0";
    }

    @Override
    public void initialize() {

    }
}

final class SLF4JLogger implements Logger {
    private static final dev.mccue.log.alpha.Logger log = LoggerFactory.getLogger();
    private static final String ARGUMENTS_KEY = "slf4j/arguments";
    private static final String MESSAGE_KEY = "slf4j/message";

    private static final String MDC_KEY = "slf4j/mdc";

    private final Log.Category category;

    SLF4JLogger(String slf4jLoggerName) {
        this.category = new Log.Category(slf4jLoggerName, "event");
    }

    private Log.Entry.Value objectToLogValue(Object o) {
        return new Log.Entry.Value.Lazy(() -> switch (o) {
            case null -> Log.Entry.Value.Null.INSTANCE;
            case String s -> new Log.Entry.Value.String(s);
            case Boolean b -> new Log.Entry.Value.Boolean(b);
            case Byte b -> new Log.Entry.Value.Byte(b);
            case Character c -> new Log.Entry.Value.Character(c);
            case Short s -> new Log.Entry.Value.Short(s);
            case Integer i -> new Log.Entry.Value.Integer(i);
            case Long l -> new Log.Entry.Value.Long(l);
            case Double d -> new Log.Entry.Value.Double(d);
            case UUID uuid -> new Log.Entry.Value.UUID(uuid);
            case URI uri -> new Log.Entry.Value.URI(uri);
            case Instant instant -> new Log.Entry.Value.Instant(instant);
            case LocalDateTime localDateTime -> new Log.Entry.Value.LocalDateTime(localDateTime);
            case LocalDate localDate -> new Log.Entry.Value.LocalDate(localDate);
            case LocalTime localTime -> new Log.Entry.Value.LocalTime(localTime);
            case Duration duration -> new Log.Entry.Value.Duration(duration);
            case Throwable t -> new Log.Entry.Value.Throwable(t);
            case List<?> list -> new Log.Entry.Value.List(list.stream().map(this::objectToLogValue).toList());
            case Set<?> set -> new Log.Entry.Value.Set(set.stream().map(this::objectToLogValue).collect(Collectors.toUnmodifiableSet()));
            case Log.Entry.Value v -> v;
            default -> new Log.Entry.Value.String(o.toString());
        });
    }

    private Log.Entry message(String s) {
        return Log.Entry.of(MESSAGE_KEY, s);
    }

    private Log.Entry mdc() {
        var contextMap = MDC.getCopyOfContextMap();
        if (contextMap == null) {
            return Log.Entry.of(MDC_KEY, Map.of());
        }
        else {
            return Log.Entry.of(MDC_KEY, contextMap.entrySet()
                    .stream()
                    .collect(Collectors.toUnmodifiableMap(
                            entry -> {
                                var key = entry.getKey();
                                return key == null ?
                                        Log.Entry.Value.Null.INSTANCE :
                                        new Log.Entry.Value.String(key);
                            },
                            entry -> {
                                var value = entry.getValue();
                                return value == null ?
                                        Log.Entry.Value.Null.INSTANCE :
                                        new Log.Entry.Value.String(value);
                            })
                    )
            );
        }
    }

    private static final Log.Entry NO_ARGS = Log.Entry.of(ARGUMENTS_KEY, List.of());

    @Override
    public String getName() {
        return this.category.namespace();
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void trace(String s) {
        log.trace(this.category, message(s));
    }

    @Override
    public void trace(String s, Object o) {
        log.trace(
                this.category,
                message(s),
                Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o))),
                mdc()
        );
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        log.trace(this.category, message(s),
                Log.Entry.of(
                        ARGUMENTS_KEY,
                        List.of(objectToLogValue(o), objectToLogValue(o1))), mdc());
    }

    @Override
    public void trace(String s, Object... objects) {
        log.trace(this.category, message(s),new Log.Entry(ARGUMENTS_KEY, objectToLogValue(Arrays.asList(objects))), mdc());
    }

    @Override
    public void trace(String s, Throwable throwable) {
        log.trace(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(throwable))), mdc());
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return true;
    }

    @Override
    public void trace(Marker marker, String s) {
        log.trace(this.category, message(s));
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        trace(s, o);
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        trace(s, o, o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        trace(s, objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        trace(s, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void debug(String s) {
        log.debug(this.category, message(s), NO_ARGS, mdc());
    }

    @Override
    public void debug(String s, Object o) {
        log.debug(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o))), mdc());
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        log.debug(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o), objectToLogValue(o1))), mdc());
    }

    @Override
    public void debug(String s, Object... objects) {
        log.debug(this.category, message(s), new Log.Entry(ARGUMENTS_KEY, objectToLogValue(Arrays.asList(objects))), mdc());
    }

    @Override
    public void debug(String s, Throwable throwable) {
        log.debug(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(throwable))), mdc());
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return true;
    }

    @Override
    public void debug(Marker marker, String s) {
        debug(s);
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        debug(s, o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        debug(s, o, o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        debug(s, objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        debug(s, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(String s) {
        log.info(this.category, message(s), NO_ARGS, mdc());
    }

    @Override
    public void info(String s, Object o) {
        log.info(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o))), mdc());
    }

    @Override
    public void info(String s, Object o, Object o1) {
        log.info(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o), objectToLogValue(o1))), mdc());
    }

    @Override
    public void info(String s, Object... objects) {
        log.info(this.category, message(s), new Log.Entry(ARGUMENTS_KEY, objectToLogValue(Arrays.asList(objects))), mdc());
    }

    @Override
    public void info(String s, Throwable throwable) {
        log.info(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(throwable))), mdc());
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public void info(Marker marker, String s) {
        info(s);
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        info(s, o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        info(s, o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        info(s, objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        info(s, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String s) {
        log.warn(this.category, message(s), NO_ARGS, mdc());
    }

    @Override
    public void warn(String s, Object o) {
        log.warn(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o))), mdc());
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        log.warn(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o), objectToLogValue(o1))), mdc());
    }

    @Override
    public void warn(String s, Object... objects) {
        log.warn(this.category, message(s), new Log.Entry(ARGUMENTS_KEY, objectToLogValue(Arrays.asList(objects))), mdc());
    }

    @Override
    public void warn(String s, Throwable throwable) {
        log.warn(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(throwable))), mdc());
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override
    public void warn(Marker marker, String s) {
        warn(s);
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        warn(s, o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        warn(s, o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        warn(s, objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        warn(s, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String s) {
        log.error(this.category, message(s), NO_ARGS, mdc());
    }

    @Override
    public void error(String s, Object o) {
        log.error(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o))), mdc());
    }

    @Override
    public void error(String s, Object o, Object o1) {
        log.error(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(o), objectToLogValue(o1))), mdc());
    }

    @Override
    public void error(String s, Object... objects) {
        log.error(this.category, message(s), new Log.Entry(ARGUMENTS_KEY, objectToLogValue(Arrays.asList(objects))), mdc());
    }

    @Override
    public void error(String s, Throwable throwable) {
        log.error(this.category, message(s), Log.Entry.of(ARGUMENTS_KEY, List.of(objectToLogValue(throwable))), mdc());
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override
    public void error(Marker marker, String s) {
        error(s);
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        error(s, o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        error(s, o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        error(s, objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        error(s, throwable);
    }
}