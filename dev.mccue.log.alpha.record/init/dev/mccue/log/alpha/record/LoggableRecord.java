package dev.mccue.log.alpha.record;

import dev.mccue.log.alpha.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a record as being a loggable entity.
 *
 * <p>Presumes that all record components are directly representable by
 * one of the types in Log.Entry.Value.</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface LoggableRecord {
    /**
     * @return Whether to use a CUPS expression in the generated code.
     */
    boolean useTypeSafeCast() default false;

    /**
     * @return The log level that the record should log at. Defaults to info.
     */
    Log.Level defaultLogLevel() default Log.Level.INFO;
}
