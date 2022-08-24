package dev.mccue.log.alpha;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public record Log(
        Context context,
        Thread thread,
        Instant timestamp,
        Flake flake,
        Level level,
        Category category,
        List<Entry> entries
) implements Iterable<Log.Entry>  {
    /*
     * Should be an extent local.
     */
    private static final AtomicReference<ThreadLocal<Context>> CONTEXT_REFERENCE =
            new AtomicReference<>(ThreadLocal.withInitial(() -> Context.Global.EMPTY));

    public Log(
            Level level,
            Category category,
            List<Log.Entry> entries
    ) {
        this(Context.current(), Thread.currentThread(), Instant.now(), Flake.create(), level, category, entries);
    }

    public static <T> T withContext(Supplier<T> code, List<Log.Entry> entries) {
        var context = CONTEXT_REFERENCE.get();
        var original = context.get();
        try {
            context.set(new Context.Child(
                    Thread.currentThread(),
                    Instant.now(),
                    Flake.create(),
                    entries,
                    original
            ));
            return code.get();
        } finally {
            context.set(original);
        }
    }

    public static <T> T withContext(Supplier<T> code, Log.Entry... entries) {
        return withContext(code, List.of(entries));
    }

    public static void withContext(Runnable code, List<Log.Entry> entries) {
        withContext(() -> {
            code.run();
            return null;
        }, entries);
    }

    public static void withContext(Runnable code, Log.Entry... entries) {
        withContext(code, List.of(entries));
    }

    public static void setGlobalContext(List<Log.Entry> entries) {
        var entriesClone = List.copyOf(entries);
        CONTEXT_REFERENCE.set(ThreadLocal.withInitial(() -> new Context.Global(entriesClone)));
    }
    public static void setGlobalContext(Log.Entry... entries) {
        setGlobalContext(Arrays.asList(entries));
    }

    /**
     * @return An Iterator over all the entries in the log, including entries in parent contexts.
     */
    @Override
    public Iterator<Entry> iterator() {
        return new Iterator<>() {
            Iterator<Entry> iter = entries.iterator();
            Context ctx = context();
            @Override
            public boolean hasNext() {
                if (iter.hasNext()) {
                    return true;
                }
                else {
                    if (ctx instanceof Context.Child childCtx) {
                        iter = childCtx.entries().iterator();
                        ctx = childCtx.parent();
                        return this.hasNext();
                    }
                    else if (ctx instanceof Context.Global globalCtx) {
                        iter = globalCtx.entries().iterator();
                        ctx = null;
                        return this.hasNext();
                    }
                    else {
                        return false;
                    }
                }
            }

            @Override
            public Entry next() {
                if (iter.hasNext()) {
                    return iter.next();
                }
                else {
                    if (ctx instanceof Context.Child childCtx) {
                        iter = childCtx.entries().iterator();
                        ctx = childCtx.parent();
                        return this.next();
                    }
                    else if (ctx instanceof Context.Global globalCtx) {
                        iter = globalCtx.entries().iterator();
                        ctx = null;
                        return this.next();
                    }
                    else {
                        throw new NoSuchElementException();
                    }
                }
            }
        };
    }

    public enum Level {
        UNSPECIFIED,
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    public sealed interface Context {
        static Context current() {
            return CONTEXT_REFERENCE.get().get();
        }

        record Global(List<Log.Entry> entries) implements Context {
            static final Global EMPTY = new Global(List.of());
        }

        record Child(
                Thread thread,
                Instant timestamp,
                Flake flake,
                List<Log.Entry> entries,
                Context parent
        ) implements Context {
        }
    }

    public record Category(String namespace, String name) {
    }

    public record Entry(String key, Value value) {

        public Entry(java.lang.String key, Value value) {
            Objects.requireNonNull(key, "Entry key must not be null");
            this.key = key;
            this.value = value == null ? Value.Null.INSTANCE : value;
        }

        public static Entry of(String key, String value) {
            return new Entry(key, value == null ? Value.Null.INSTANCE : new Value.String(value));
        }

        public static Entry of(String key, boolean value) {
            return new Entry(key, new Value.Boolean(value));
        }

        public static Entry of(String key, byte value) {
            return new Entry(key, new Value.Byte(value));
        }

        public static Entry of(String key, char value) {
            return new Entry(key, new Value.Character(value));
        }

        public static Entry of(String key, short value) {
            return new Entry(key, new Value.Short(value));
        }

        public static Entry of(String key, int value) {
            return new Entry(key, new Value.Integer(value));
        }

        public static Entry of(String key, long value) {
            return new Entry(key, new Value.Long(value));
        }

        public static Entry of(String key, double value) {
            return new Entry(key, new Value.Double(value));
        }

        public static Entry of(String key, java.lang.Boolean value) {
            return of(key, value, Value.Boolean::new);
        }

        public static Entry of(String key, java.lang.Byte value) {
            return of(key, value, Value.Byte::new);
        }

        public static Entry of(String key, java.lang.Character value) {
            return of(key, value, Value.Character::new);
        }

        public static Entry of(String key, java.lang.Short value) {
            return of(key, value, Value.Short::new);
        }

        public static Entry of(String key, java.lang.Integer value) {
            return of(key, value, Value.Integer::new);
        }

        public static Entry of(String key, java.lang.Long value) {
            return of(key, value, Value.Long::new);
        }

        public static Entry of(String key, java.lang.Double value) {
            return of(key, value, Value.Double::new);
        }

        public static Entry of(String key, java.util.UUID value) {
            return of(key, value, Value.UUID::new);
        }

        public static Entry of(String key, java.net.URI value) {
            return of(key, value, Value.URI::new);
        }

        public static Entry of(String key, java.time.Instant value) {
            return of(key, value, Value.Instant::new);
        }

        public static Entry of(String key, java.time.LocalDateTime value) {
            return of(key, value, Value.LocalDateTime::new);
        }

        public static Entry of(String key, java.time.LocalDate value) {
            return of(key, value, Value.LocalDate::new);
        }

        public static Entry of(String key, java.time.LocalTime value) {
            return of(key, value, Value.LocalTime::new);
        }

        public static Entry of(String key, java.time.Duration value) {
            return of(key, value, Value.Duration::new);
        }

        public static Entry of(String key, java.lang.Throwable value) {
            return of(key, value, Value.Throwable::new);
        }

        public static Entry of(String key, List<Value> value) {
            return of(key, value, Value.List::new);
        }

        public static Entry of(String key, Map<Value, Value> value) {
            return of(key, value, Value.Map::new);
        }

        public static <T> Entry of(String key, T value, Function<T, Value> toValue) {
            return new Entry(key, value == null ? Value.Null.INSTANCE : toValue.apply(value));
        }

        public static Entry of(String key, Supplier<Value> valueSupplier) {
            var value = valueSupplier.get();
            return new Entry(key, value == null ? Value.Null.INSTANCE : value);
        }

        public static Entry ofLazy(String key, Supplier<Value> valueSupplier) {
            return new Entry(key, new Value.Lazy(valueSupplier));
        }

        public static <T> Entry ofLazy(String key, T value, Function<T, Value> toValue) {
            return new Entry(key, new Value.Lazy(() -> toValue.apply(value)));
        }

        /**
         * A loggable value.
         *
         * <p>Only supports a subset of "basic" data kinds</p>
         */
        public sealed interface Value {
            /**
             * null
             */
            enum Null implements Value {
                INSTANCE;

                @Override
                public java.lang.String toString() {
                    return "Null";
                }
            }

            /**
             * A String.
             *
             * @param value The {@link java.lang.String} being wrapped. Must not be null.
             */
            record String(java.lang.String value) implements Value {
                public String {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * A boolean.
             *
             * @param value A wrapped boolean.
             */
            record Boolean(boolean value) implements Value {
            }

            /**
             * A byte.
             *
             * @param value A wrapped byte.
             */
            record Byte(byte value) implements Value {
            }

            /**
             * A char.
             *
             * @param value A wrapped char.
             */
            record Character(char value) implements Value {
            }

            /**
             * A short.
             *
             * @param value A wrapped shirt.
             */
            record Short(short value) implements Value {
            }

            /**
             * An int.
             *
             * @param value A wrapped int.
             */
            record Integer(int value) implements Value {
            }

            /**
             * A long.
             *
             * @param value A wrapped long.
             */
            record Long(long value) implements Value {
            }

            /**
             * A double.
             *
             * @param value A wrapped double.
             */
            record Double(double value) implements Value {
            }

            /**
             * A UUID.
             *
             * @param value A wrapped {@link java.util.UUID}.
             */
            record UUID(java.util.UUID value) implements Value {
                /**
                 * @param value The {@link java.util.UUID} being wrapped. Must not be null.
                 */
                public UUID {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * A URI.
             *
             * @param value A wrapped {@link java.net.URI}.
             */
            record URI(java.net.URI value) implements Value {
                /**
                 * @param value The {@link java.net.URI} being wrapped. Must not be null.
                 */
                public URI {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * An instant in time.
             *
             * @param value A wrapped {@link java.time.Instant}.
             */
            record Instant(java.time.Instant value) implements Value {
                /**
                 * @param value The {@link java.time.Instant} being wrapped. Must not be null.
                 */
                public Instant {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * A local date-time.
             *
             * @param value A wrapped {@link java.time.LocalDateTime}.
             */
            record LocalDateTime(java.time.LocalDateTime value) implements Value {
                /**
                 * @param value The {@link java.time.LocalDateTime} being wrapped. Must not be null.
                 */
                public LocalDateTime {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * A local date without a time component.
             *
             * @param value A wrapped {@link java.time.LocalDate}.
             */
            record LocalDate(java.time.LocalDate value) implements Value {
                /**
                 * @param value The {@link java.time.LocalDate} being wrapped. Must not be null.
                 */
                public LocalDate {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * A local time without a date component.
             *
             * @param value A wrapped {@link java.time.LocalTime}.
             */
            record LocalTime(java.time.LocalTime value) implements Value {
                /**
                 * @param value The {@link java.time.LocalTime} being wrapped. Must not be null.
                 */
                public LocalTime {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * A duration of time.
             *
             * @param value A wrapped {@link java.time.Duration}.
             */
            record Duration(java.time.Duration value) implements Value {
                /**
                 * @param value The {@link java.time.Duration} being wrapped. Must not be null.
                 */
                public Duration {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * A throwable value. Represents an error that occurred when doing some computation.
             *
             * @param value The throwable that was thrown.
             */
            record Throwable(java.lang.Throwable value) implements Value {
                /**
                 * @param value The {@link java.lang.Throwable} being wrapped.  Must not be null.
                 */
                public Throwable {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            /**
             * A list of values.
             *
             * @param value The underlying {@link java.util.List}. Will be unmodifiable.
             */
            record List(java.util.List<Value> value) implements Value {
                /**
                 * Constructs a new List.
                 *
                 * @param value The list of values to use to construct the list. Will copy the list
                 *              and replace stray null values. Should not be null.
                 */
                public List(java.util.List<Value> value) {
                    Objects.requireNonNull(value, "value must not be null");
                    this.value = value.stream()
                            .map(v -> v == null ? Value.Null.INSTANCE : v)
                            .toList();
                }
            }

            /**
             * A map of value to value.
             *
             * @param value The underlying {@link java.util.Map}. Will be unmodifiable.
             */
            record Map(java.util.Map<Value, Value> value) implements Value {
                /**
                 * Constructs a new Map.
                 *
                 * @param value The map of values to use to construct the map. Will copy the map
                 *              and replace stray null values. Should not be null.
                 */
                public Map(java.util.Map<Value, Value> value) {
                    Objects.requireNonNull(value, "value must not be null");
                    this.value = value.entrySet()
                            .stream()
                            .collect(Collectors.toUnmodifiableMap(
                                    entry -> entry.getKey() == null ? Null.INSTANCE : entry.getKey(),
                                    entry -> entry.getValue() == null ? Null.INSTANCE : entry.getValue()
                            ));
                }
            }

            /**
             * A set of values.
             *
             * <p>This is different from a list in that it is implied that no duplicates are
             * allowed and order is not assumed.</p>
             *
             * @param value The underlying {@link java.util.Set}. Will be unmodifiable.
             */
            record Set(java.util.Set<Value> value) implements Value {
                /**
                 * Constructs a new Set.
                 *
                 * @param value The set of values to use to construct the set. Will copy the set
                 *              and replace stray null values. Should not be null.
                 */
                public Set(java.util.Set<Value> value) {
                    Objects.requireNonNull(value, "value must not be null");
                    this.value = value.stream()
                            .map(v -> v == null ? Null.INSTANCE : v)
                            .collect(Collectors.toUnmodifiableSet());
                }
            }

            /**
             * A lazily realized value.
             *
             * <p>When its value is requested it will be computed. If any exceptions
             * occur when realizing its Value then a Value.Throwable will be returned.</p>
             *
             * <p>After a value is computed it is stable and will not be recomputed.</p>
             */
            final class Lazy implements Value {
                // Implementation based off of clojure's Delay + vavr's Lazy
                private volatile Supplier<? extends Value> supplier;
                private Value value;


                /**
                 * Constructs a Lazy value from the given supplier.
                 *
                 * @param supplier Code which will be called later to provide a value.
                 */
                public Lazy(Supplier<? extends Value> supplier) {
                    Objects.requireNonNull(supplier, "supplier must not be null");
                    this.supplier = supplier;
                    this.value = null;
                }

                /**
                 * @return The computed {@link Value}. Will return the same value on repeat calls. Safe to call from
                 * multiple threads.
                 */
                public Value value() {
                    if (supplier != null) {
                        synchronized (this) {
                            final var s = supplier;
                            if (s != null) {
                                try {
                                    this.value = Objects.requireNonNullElse(s.get(), Null.INSTANCE);
                                } catch (java.lang.Throwable throwable) {
                                    this.value = new Value.Throwable(throwable);
                                }
                                this.supplier = null;
                            }
                        }
                    }

                    return this.value;
                }

                @Override
                public java.lang.String toString() {
                    if (supplier == null) {
                        return "Lazy[pending]";
                    }
                    else {
                        return "Lazy[realized: value=" + value() + "]";
                    }
                }
            }
        }
    }
}
