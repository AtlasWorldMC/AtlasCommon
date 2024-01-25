package fr.atlasworld.common.logging;

import fr.atlasworld.common.logging.stream.LoggingOutputStream;
import fr.atlasworld.common.reflection.ReflectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

public final class LogUtils {
    private static final Logger LOGGER = getLogger();

    private static final Logger SYS_ERR_LOGGER = LoggerFactory.getLogger("SYSERR");
    private static final Logger SYS_OUT_LOGGER = LoggerFactory.getLogger("SYSOUT");

    static {
        System.setOut(new PrintStream(new LoggingOutputStream(SYS_OUT_LOGGER, Level.INFO), true));
        System.setErr(new PrintStream(new LoggingOutputStream(SYS_ERR_LOGGER, Level.ERROR), true));
    }

    public static Logger getLogger() {
        return LoggerFactory.getLogger(ReflectionFactory.STACK_WALKER.getCallerClass().getSimpleName());
    }

    public static Logger getLoggerFullName() {
        return LoggerFactory.getLogger(ReflectionFactory.STACK_WALKER.getCallerClass());
    }

    public static void disableLogger(Logger logger) {
        setLevel(logger, Level.OFF);
    }

    public static void disableLogger(String logger) {
        setLevel(logger, Level.OFF);
    }

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

    public static void setLevel(Logger logger, Level level) {
        setLevel(logger.getName(), level);
    }

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

    private static void setVanillaLoggingLevel(String logger, java.util.logging.Level level) {
        java.util.logging.Logger.getLogger(logger).setLevel(level);
    }

    private static void setLog4JLevel(String logger, org.apache.logging.log4j.Level level) {
        org.apache.logging.log4j.core.config.Configurator.setAllLevels(logger, level);
    }

    private static void setLogBackLevel(String logger, ch.qos.logback.classic.Level level) {
        ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(logger);
        logbackLogger.setLevel(level);
    }
}
