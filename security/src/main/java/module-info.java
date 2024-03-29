module fr.atlasworld.common.security {
    requires fr.atlasworld.common;

    requires com.google.common;
    requires org.jetbrains.annotations;

    exports fr.atlasworld.common.security;
    exports fr.atlasworld.common.security.encryptor;
    exports fr.atlasworld.common.security.exception; // TODO: Deprecated: Remove before 1.1.0
}