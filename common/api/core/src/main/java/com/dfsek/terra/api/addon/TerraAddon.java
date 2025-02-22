package com.dfsek.terra.api.addon;


import org.jetbrains.annotations.NotNull;

import com.dfsek.terra.api.addon.annotations.Addon;
import com.dfsek.terra.api.addon.annotations.Author;
import com.dfsek.terra.api.addon.annotations.Version;


/**
 * Represents an entry point for an com.dfsek.terra.addon. Implementations must be annotated with {@link Addon}.
 */
public abstract class TerraAddon {
    /**
     * Invoked immediately after an com.dfsek.terra.addon is loaded.
     */
    public abstract void initialize();
    
    /**
     * Gets the version of this com.dfsek.terra.addon.
     *
     * @return Addon version.
     */
    public final @NotNull String getVersion() {
        Version version = getClass().getAnnotation(Version.class);
        return version == null ? "0.1.0" : version.value();
    }
    
    /**
     * Gets the author of this com.dfsek.terra.addon.
     *
     * @return Addon author.
     */
    public final @NotNull String getAuthor() {
        Author author = getClass().getAnnotation(Author.class);
        return author == null ? "Anon Y. Mous" : author.value();
    }
    
    /**
     * Gets the name (ID) of this com.dfsek.terra.addon.
     *
     * @return Addon ID.
     */
    public final @NotNull String getName() {
        Addon addon = getClass().getAnnotation(Addon.class);
        if(addon == null)
            throw new IllegalStateException(
                    "Addon annotation not present"); // This should never happen; the presence of this annotation is checked by the com
        // .dfsek.terra.addon loader.
        return addon.value();
    }
}
