package dev.mccue.log.alpha;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public record Log(
        Context context,
        Thread thread,
        Instant timestamp,
        Flake flake,
        Level level,
        Log.Type type,
        List<Log.Entry> entries
) {
    /**
     * Should be an extent local, and that is a decent enough thing to be part of the public API.
     */
    private static final ThreadLocal<Context> CONTEXT;

    static {
        CONTEXT = new ThreadLocal<>();
        CONTEXT.set(new Context.Empty());
    }

    public Log(
            Instant timestamp,
            Flake flake,
            Level level,
            Log.Type type,
            List<Log.Entry> entries
    ) {
        this(CONTEXT.get(), Thread.currentThread(), timestamp, flake, level, type, entries);
    }

    public static <T> T inContext(Supplier<T> code, List<Log.Entry> entries) {
        var original = CONTEXT.get();
        try {
            CONTEXT.set(new Context.NotEmpty(
                    Thread.currentThread(),
                    Instant.now(),
                    Flake.create(),
                    entries,
                    original
            ));
            return code.get();
        } finally {
            CONTEXT.set(original);
        }
    }

    public static <T> T inContext(Supplier<T> code, Log.Entry... entries) {
        return inContext(code, List.of(entries));
    }

    public static void inContext(Runnable code, List<Log.Entry> entries) {
        var original = CONTEXT.get();
        try {
            CONTEXT.set(new Context.NotEmpty(
                    Thread.currentThread(),
                    Instant.now(),
                    Flake.create(),
                    entries,
                    original
            ));
            code.run();
        } finally {
            CONTEXT.set(original);
        }
    }

    public static void inContext(Runnable code, Log.Entry entries) {
        inContext(code, List.of(entries));
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
            return CONTEXT.get();
        }

        record Empty() implements Context {
        }

        record NotEmpty(
                Thread thread,
                Instant timestamp,
                Flake flake,
                List<Log.Entry> entries,
                Context parent
        ) implements Context {
        }
    }

    public record Type(String namespace, String name) {
    }

    public record Entry(String key, Value value) {

        public Entry(java.lang.String key, Value value) {
            Objects.requireNonNull(key, "Entry key must not be null");
            this.key = key;
            this.value = value == null ? new Value.Null() : value;
        }

        public static Entry of(String key, String value) {
            return new Entry(key, value == null ? new Value.Null() : new Value.String(value));
        }

        public static Entry of(String key, boolean value) {
            return new Entry(key, new Value.Boolean(value));
        }

        public static Entry of(String key, char value) {
            return new Entry(key, new Value.Char(value));
        }

        public static Entry of(String key, short value) {
            return new Entry(key, new Value.Short(value));
        }

        public static Entry of(String key, int value) {
            return new Entry(key, new Value.Int(value));
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

        public static Entry of(String key, java.lang.Character value) {
            return of(key, value, Value.Char::new);
        }

        public static Entry of(String key, java.lang.Short value) {
            return of(key, value, Value.Short::new);
        }

        public static Entry of(String key, java.lang.Integer value) {
            return of(key, value, Value.Int::new);
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

        public static Entry of(String key, Map<String, Value> value) {
            return of(key, value, Value.Map::new);
        }

        public static <T> Entry of(String key, T value, Function<T, Value> toValue) {
            return new Entry(key, value == null ? new Value.Null() : toValue.apply(value));
        }

        public static <T> Entry of(String key, Supplier<Value> valueSupplier) {
            var value = valueSupplier.get();
            return new Entry(key, value == null ? new Value.Null() : value);
        }

        public sealed interface Value {
            record Null() implements Value {
            }

            record String(java.lang.String value) implements Value {
                public String {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record Boolean(boolean value) implements Value {
            }

            record Char(char value) implements Value {
            }

            record Short(short value) implements Value {
            }

            record Int(int value) implements Value {
            }

            record Long(long value) implements Value {
            }

            record Double(double value) implements Value {
            }

            record UUID(java.util.UUID value) implements Value {
                public UUID {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record URI(java.net.URI value) implements Value {
                public URI {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record Instant(java.time.Instant value) implements Value {
                public Instant {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record LocalDateTime(java.time.LocalDateTime value) implements Value {
                public LocalDateTime {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record LocalDate(java.time.LocalDate value) implements Value {
                public LocalDate {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record LocalTime(java.time.LocalTime value) implements Value {
                public LocalTime {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record Duration(java.time.Duration value) implements Value {
                public Duration {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record Throwable(java.lang.Throwable value) implements Value {
                public Throwable {
                    Objects.requireNonNull(value, "value must not be null");
                }
            }

            record List(java.util.List<Value> value) implements Value {
                public List(java.util.List<Value> value) {
                    Objects.requireNonNull(value, "value must not be null");
                    this.value = java.util.List.copyOf(value);
                }
            }

            record Map(java.util.Map<java.lang.String, Value> value) implements Value {
                public Map(java.util.Map<java.lang.String, Value> value) {
                    Objects.requireNonNull(value, "value must not be null");
                    this.value = java.util.Map.copyOf(value);
                }
            }

            record Set(java.util.Set<Value> value) implements Value {
                public Set(java.util.Set<Value> value) {
                    Objects.requireNonNull(value, "value must not be null");
                    this.value = java.util.Set.copyOf(value);
                }
            }
        }
    }
}
