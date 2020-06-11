package nl.kingdev.jcraft.client.world;

import nl.kingdev.jcraft.client.world.chunk.Chunk;
import nl.kingdev.jcraft.engine.intefaces.IDestroyable;
import org.joml.Vector3i;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World implements IDestroyable {




    private List<Chunk> chunks = new ArrayList<>();


    public void build() {

        chunks.add(new Chunk(new Vector3i(0,0,0)));
        chunks.add(new Chunk(new Vector3i(1,0,0)));


    }


    public List<Chunk> getChunks() {
        return chunks;
    }

    @Override
    public void destroy() {
      chunks.forEach(chunk -> {
          chunk.destroy();
      });
    }
}
