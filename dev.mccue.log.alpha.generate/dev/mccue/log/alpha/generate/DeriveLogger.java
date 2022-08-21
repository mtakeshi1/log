package dev.mccue.log.alpha.generate;

/**
 * Generates a call to get a logger.
 */
public @interface DeriveLogger {
    boolean useTypeSafeCast() default false;
}
