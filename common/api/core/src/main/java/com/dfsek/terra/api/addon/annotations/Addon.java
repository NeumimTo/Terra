package com.dfsek.terra.api.addon.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Specifies that the annotated class is an entry point for a Terra com.dfsek.terra.addon.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Addon {
    /**
     * @return The ID of the com.dfsek.terra.addon.
     */
    @NotNull String value();
}
