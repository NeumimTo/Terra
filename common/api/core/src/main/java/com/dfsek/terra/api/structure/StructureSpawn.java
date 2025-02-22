package com.dfsek.terra.api.structure;

import com.dfsek.terra.api.util.vector.Vector3;


public interface StructureSpawn {
    /**
     * Get nearest spawn point
     *
     * @param x    X coordinate
     * @param z    Z coordinate
     * @param seed Seed for RNG
     *
     * @return Vector representing nearest spawnpoint
     */
    Vector3 getNearestSpawn(int x, int z, long seed);
}
