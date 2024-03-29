module fr.atlasworld.common.concurrent {
    requires fr.atlasworld.common;

    requires com.google.common;
    requires com.google.errorprone.annotations;
    requires org.jetbrains.annotations;

    exports fr.atlasworld.common.concurrent;
    exports fr.atlasworld.common.concurrent.action;
    exports fr.atlasworld.common.concurrent.exception; // TODO: Deprecated: Remove before 1.1.0
}