package fr.atlasworld.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to indicate that a builder argument is required.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RequiredBuilderArgument {
}
