package com.dfsek.terra.api.registry;

import com.dfsek.terra.api.registry.exception.DuplicateEntryException;


public interface CheckedRegistry<T> extends Registry<T> {
    /**
     * Add a value to this registry, checking whether it is present first.
     *
     * @param identifier Identifier to assign value.
     * @param value      Value to register.
     *
     * @throws DuplicateEntryException If an entry with the same identifier is already present.
     */
    void register(String identifier, T value) throws DuplicateEntryException;
    
}
