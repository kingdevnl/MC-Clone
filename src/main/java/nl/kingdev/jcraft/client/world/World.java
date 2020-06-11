package nl.kingdev.jcraft.client.world;

import nl.kingdev.jcraft.client.world.chunk.Chunk;
import nl.kingdev.jcraft.engine.intefaces.IDestroyable;
import nl.kingdev.jcraft.engine.utils.FastNoise;
import org.joml.Vector3i;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World implements IDestroyable {




    private List<Chunk> chunks = new ArrayList<>();


    public void build() {

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                chunks.add(new Chunk(new Vector3i(i,0,j)));

            }
        }




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
