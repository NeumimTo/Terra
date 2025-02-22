package com.dfsek.terra.api.world;

import java.util.List;

import com.dfsek.terra.api.util.Range;
import com.dfsek.terra.api.util.vector.Vector3;


public interface Flora {
    boolean plant(Vector3 l, World world);
    
    List<Vector3> getValidSpawnsAt(Chunk chunk, int x, int z, Range check);
}
