package com.dfsek.terra.api.structure;

import java.util.Random;

import com.dfsek.terra.api.util.StringIdentifiable;
import com.dfsek.terra.api.structure.buffer.Buffer;
import com.dfsek.terra.api.structure.rotation.Rotation;
import com.dfsek.terra.api.util.vector.Vector3;
import com.dfsek.terra.api.world.Chunk;
import com.dfsek.terra.api.world.World;


public interface Structure extends StringIdentifiable {
    boolean generate(Vector3 location, World world, Chunk chunk, Random random, Rotation rotation);
    
    boolean generate(Buffer buffer, World world, Random random, Rotation rotation, int recursions);
    
    boolean generate(Vector3 location, World world, Random random, Rotation rotation);
}
