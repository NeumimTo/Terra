package com.dfsek.terra.config.loaders;

import com.dfsek.tectonic.exception.LoadException;
import com.dfsek.tectonic.loading.ConfigLoader;
import com.dfsek.tectonic.loading.TypeLoader;

import java.lang.reflect.AnnotatedType;
import java.util.List;

import com.dfsek.terra.api.block.BlockType;
import com.dfsek.terra.api.util.collection.MaterialSet;


@SuppressWarnings("unchecked")
public class MaterialSetLoader implements TypeLoader<MaterialSet> {
    @Override
    public MaterialSet load(AnnotatedType type, Object o, ConfigLoader configLoader) throws LoadException {
        List<String> stringData = (List<String>) o;
        MaterialSet set = new MaterialSet();
        
        for(String string : stringData) {
            try {
                set.add(configLoader.loadType(BlockType.class, string));
            } catch(NullPointerException e) {
                throw new LoadException("Invalid data identifier \"" + string + "\"", e);
            }
        }
        
        return set;
    }
}
