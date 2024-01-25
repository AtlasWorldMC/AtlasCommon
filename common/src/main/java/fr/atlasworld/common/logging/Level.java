package fr.atlasworld.common.logging;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Logging levels.
 */
public enum Level {

    /**
     * Disables the logger.
     */
    OFF,

    /**
     * Logger will logger {@code TRACE} to {@code ERROR} messages.
     */
    TRACE,

    /**
     * Logger will logger {@code DEBUG} to {@code ERROR} messages.
     */
    DEBUG,

    /**
     * Logger will logger {@code INFO} to {@code ERROR} messages.
     */
    INFO,

    /**
     * Logger will logger {@code WARNING} and {@code ERROR} messages.
     */
    WARNING,

    /**
     * Logger will only logger {@code ERROR} messages.
     */
    ERROR;

    /**
     * Converts this level to a {@code SLF4J} level format.
     * @return null if the level is {@code OFF}.
     */
    public org.slf4j.event.Level convertToSlf4J() {
        return switch (this) {
            case OFF -> null;
            case TRACE -> org.slf4j.event.Level.TRACE;
            case DEBUG -> org.slf4j.event.Level.DEBUG;
            case INFO -> org.slf4j.event.Level.INFO;
            case WARNING -> org.slf4j.event.Level.WARN;
            case ERROR -> org.slf4j.event.Level.ERROR;
        };
    }

    /**
     * Converts this level to a {@link java.util.logging.Level} level format.
     * @return this level into a{@link java.util.logging.Level} level format.
     */
    public java.util.logging.Level convertToVanilla() {
        return switch (this) {
            case OFF -> java.util.logging.Level.OFF;
            case TRACE -> java.util.logging.Level.FINER;
            case DEBUG -> java.util.logging.Level.FINE;
            case INFO -> java.util.logging.Level.INFO;
            case WARNING -> java.util.logging.Level.WARNING;
            case ERROR -> java.util.logging.Level.SEVERE;
        };
    }

    /**
     * Converts this level to a {@code LOG4J} level format.
     * @return this level into a {@code LOG4J} level format.
     */
    public org.apache.logging.log4j.Level convertToLog4J() {
        return switch (this) {
            case OFF -> org.apache.logging.log4j.Level.OFF;
            case TRACE -> org.apache.logging.log4j.Level.TRACE;
            case DEBUG -> org.apache.logging.log4j.Level.DEBUG;
            case INFO -> org.apache.logging.log4j.Level.INFO;
            case WARNING -> org.apache.logging.log4j.Level.WARN;
            case ERROR -> org.apache.logging.log4j.Level.ERROR;
        };
    }

    /**
     * Converts this level to a {@code LogBack} level format.
     * @return this level into a {@code LogBack} level format.
     */
    public ch.qos.logback.classic.Level convertToLogBack() {
        return switch (this) {
            case OFF -> ch.qos.logback.classic.Level.OFF;
            case TRACE -> ch.qos.logback.classic.Level.TRACE;
            case DEBUG -> ch.qos.logback.classic.Level.DEBUG;
            case INFO -> ch.qos.logback.classic.Level.INFO;
            case WARNING -> ch.qos.logback.classic.Level.WARN;
            case ERROR -> ch.qos.logback.classic.Level.ERROR;
        };
    }

    /**
     * Converts a {@code SLF4J} level into a AtlasCommon level format.
     * @param level level to convert
     * @return provided level into a AtlasCommon level format.
     */
    public static Level convert(@NotNull org.slf4j.event.Level level) {
        Preconditions.checkNotNull(level);

        return switch (level) {
            case TRACE -> TRACE;
            case DEBUG -> DEBUG;
            case INFO -> INFO;
            case WARN -> WARNING;
            case ERROR -> ERROR;
        };
    }

    /**
     * Converts a {@link java.util.logging.Level} level into a AtlasCommon level format.
     * @param level level to convert
     * @return provided level into a AtlasCommon level format.
     */
    public static Level convert(@NotNull java.util.logging.Level level) {
        Preconditions.checkNotNull(level);

        if (level == java.util.logging.Level.OFF)
            return OFF;

        if (level == java.util.logging.Level.ALL || level == java.util.logging.Level.FINEST || level == java.util.logging.Level.FINER)
            return TRACE;

        if (level == java.util.logging.Level.FINE || level == java.util.logging.Level.CONFIG)
            return DEBUG;

        if (level == java.util.logging.Level.INFO)
            return INFO;

        if (level == java.util.logging.Level.WARNING)
            return WARNING;

        if (level == java.util.logging.Level.SEVERE)
            return ERROR;

        throw new IllegalArgumentException("If you're seeing this message, " +
                "or this library is not up-to-date or there is something wrong with this classpath! Did you provide a custom level?");
    }

    /**
     * Converts a {@code LOG4J} level into a AtlasCommon level format.
     * @param level level to convert
     * @return provided level into a AtlasCommon level format.
     */
    public static Level convert(@NotNull org.apache.logging.log4j.Level level) {
        Preconditions.checkNotNull(level);

        if (level == org.apache.logging.log4j.Level.OFF)
            return OFF;

        if (level == org.apache.logging.log4j.Level.TRACE || level == org.apache.logging.log4j.Level.ALL)
            return TRACE;

        if (level == org.apache.logging.log4j.Level.DEBUG)
            return DEBUG;

        if (level == org.apache.logging.log4j.Level.INFO)
            return INFO;

        if (level == org.apache.logging.log4j.Level.WARN)
            return WARNING;

        if (level == org.apache.logging.log4j.Level.ERROR || level == org.apache.logging.log4j.Level.FATAL)
            return ERROR;

        throw new IllegalArgumentException("If you're seeing this message, " +
                "or this library is not up-to-date or there is something wrong with this classpath! Did you provide a custom level?");
    }

    /**
     * Converts a {@code LogBack} level into a AtlasCommon level format.
     * @param level level to convert
     * @return provided level into a AtlasCommon level format.
     */
    public static Level convert(@NotNull ch.qos.logback.classic.Level level) {
        Preconditions.checkNotNull(level);

        if (level == ch.qos.logback.classic.Level.OFF)
            return OFF;

        if (level == ch.qos.logback.classic.Level.ALL || level == ch.qos.logback.classic.Level.TRACE)
            return TRACE;

        if (level == ch.qos.logback.classic.Level.DEBUG)
            return DEBUG;

        if (level == ch.qos.logback.classic.Level.INFO)
            return INFO;

        if (level == ch.qos.logback.classic.Level.WARN)
            return WARNING;

        if (level == ch.qos.logback.classic.Level.ERROR)
            return ERROR;

        throw new IllegalArgumentException("If you're seeing this message, " +
                "or this library is not up-to-date or there is something wrong with this classpath! Did you provide a custom level?");
    }
}
