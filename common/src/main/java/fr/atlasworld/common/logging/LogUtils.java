package fr.atlasworld.common.logging;

import fr.atlasworld.common.logging.stream.LoggingOutputStream;
import fr.atlasworld.common.reflection.ReflectionFactory;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

/**
 * LogUtils, Logging utility class.
 */
public final class LogUtils {
    private static final Logger LOGGER = getLoggerFullName();

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion = "1.1.0")
    private static final Logger SYS_ERR_LOGGER = LoggerFactory.getLogger("SYSERR");
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion = "1.1.0")
    private static final Logger SYS_OUT_LOGGER = LoggerFactory.getLogger("SYSOUT");

    /**
     * Retrieve the logger for the caller class.
     * <p>
     * This method is called sensitive.
     *
     * @return returns a logger with the name of the class.
     */
    public static Logger getLogger() {
        return LoggerFactory.getLogger(ReflectionFactory.STACK_WALKER.getCallerClass().getSimpleName());
    }

    /**
     * Retrieve the logger for the caller class.
     * <p>
     * This method is called sensitive.
     *
     * @return returns a logger with the name and the package of the class.
     */
    public static Logger getLoggerFullName() {
        return LoggerFactory.getLogger(ReflectionFactory.STACK_WALKER.getCallerClass());
    }

    /**
     * Disable a specified logger.
     *
     * @param logger logger to disable.
     */
    public static void disableLogger(Logger logger) {
        setLevel(logger, Level.OFF);
    }

    /**
     * Disable a specified logger.
     *
     * @param logger name of the logger to disable.
     */
    public static void disableLogger(String logger) {
        setLevel(logger, Level.OFF);
    }

    /**
     * Sets the global logging level of the environment.
     * <p>
     * This will override the set logging level in your logging framework configuration file.
     *
     * @param level level to set.
     */
    public static void setGlobalLevel(Level level) {
        setVanillaLoggingLevel("", level.convertToVanilla());

        if (ReflectionFactory.classExists("org.apache.logging.log4j.core.config.Configurator"))
            setLog4JLevel(org.apache.logging.log4j.LogManager.ROOT_LOGGER_NAME, level.convertToLog4J());
        else
            LOGGER.trace("Could not find LOG4J core, skipping binding..");

        if (ReflectionFactory.classExists("ch.qos.logback.classic.Logger"))
            setLogBackLevel(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME, level.convertToLogBack());
        else
            LOGGER.trace("Could not find LOGBACK classic, skipping binding..");

        LOGGER.debug("Changed global logging level to {}", level);
    }

    /**
     * Sets the level of a specified logger.
     *
     * @param level level to set the logger.
     * @param logger logger.
     */
    public static void setLevel(Logger logger, Level level) {
        setLevel(logger.getName(), level);
    }

    /**
     * Sets the level of a specified logger.
     *
     * @param level level to set the logger.
     * @param logger name of the logger.
     */
    public static void setLevel(String logger, Level level) {
        setVanillaLoggingLevel(logger, level.convertToVanilla());

        if (ReflectionFactory.classExists("org.apache.logging.log4j.core.config.Configurator"))
            setLog4JLevel(logger, level.convertToLog4J());
        else
            LOGGER.trace("Could not find LOG4J core, skipping binding..");

        if (ReflectionFactory.classExists("ch.qos.logback.classic.Logger"))
            setLogBackLevel(logger, level.convertToLogBack());
        else
            LOGGER.trace("Could not find LOGBACK classic, skipping binding..");


        LOGGER.debug("Changed '{}' logging level to {}", logger, level);
    }

    /**
     * Forces {@code System.out} and {@code System.err} PrintStreams to use {@link LoggingOutputStream}.
     *
     * @deprecated Logging Output streams should be set by the user directly.
     */
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion = "1.1.0")
    public static void updateOutStreams() {
        System.setOut(new PrintStream(new LoggingOutputStream(SYS_OUT_LOGGER, Level.INFO), true));
        System.setErr(new PrintStream(new LoggingOutputStream(SYS_ERR_LOGGER, Level.ERROR), true));
    }

    /**
     * Internal Method
     */
    @ApiStatus.Internal
    private static void setVanillaLoggingLevel(String logger, java.util.logging.Level level) {
        java.util.logging.Logger.getLogger(logger).setLevel(level);
    }

    /**
     * Internal Method
     */
    @ApiStatus.Internal
    private static void setLog4JLevel(String logger, org.apache.logging.log4j.Level level) {
        org.apache.logging.log4j.core.config.Configurator.setAllLevels(logger, level);
    }

    /**
     * Internal Method
     */
    @ApiStatus.Internal
    private static void setLogBackLevel(String logger, ch.qos.logback.classic.Level level) {
        ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(logger);
        logbackLogger.setLevel(level);
    }
}
