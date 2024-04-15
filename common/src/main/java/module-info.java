module fr.atlasworld.common {
    requires java.logging;

    requires com.google.gson;
    requires com.google.common;
    requires ch.qos.logback.classic;
    requires org.jetbrains.annotations;
    requires org.slf4j;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    exports fr.atlasworld.common.annotation;
    exports fr.atlasworld.common.compound;
    exports fr.atlasworld.common.compound.json;
    exports fr.atlasworld.common.exception;
    exports fr.atlasworld.common.file.reader;
    exports fr.atlasworld.common.logging;
    exports fr.atlasworld.common.logging.stream;
    exports fr.atlasworld.common.reflection;
}