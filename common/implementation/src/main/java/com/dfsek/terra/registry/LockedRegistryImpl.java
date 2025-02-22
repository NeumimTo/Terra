package com.dfsek.terra.registry;

import com.dfsek.tectonic.exception.LoadException;
import com.dfsek.tectonic.loading.ConfigLoader;

import java.lang.reflect.AnnotatedType;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.dfsek.terra.api.registry.Registry;


/**
 * Wrapper for a registry that forbids all write access.
 *
 * @param <T> Type in registry
 */
public class LockedRegistryImpl<T> implements Registry<T> {
    private final Registry<T> registry;
    
    public LockedRegistryImpl(Registry<T> registry) {
        this.registry = registry;
    }
    
    @Override
    public T get(String identifier) {
        return registry.get(identifier);
    }
    
    @Override
    public boolean contains(String identifier) {
        return registry.contains(identifier);
    }
    
    @Override
    public void forEach(Consumer<T> consumer) {
        registry.forEach(consumer);
    }
    
    @Override
    public void forEach(BiConsumer<String, T> consumer) {
        registry.forEach(consumer);
    }
    
    @Override
    public Collection<T> entries() {
        return registry.entries();
    }
    
    @Override
    public Set<String> keys() {
        return registry.keys();
    }
    
    @Override
    public T load(AnnotatedType t, Object c, ConfigLoader loader) throws LoadException {
        return registry.load(t, c, loader);
    }
}
