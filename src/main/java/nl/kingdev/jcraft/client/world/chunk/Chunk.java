package nl.kingdev.jcraft.client.world.chunk;

import nl.kingdev.jcraft.client.Textures;
import nl.kingdev.jcraft.client.world.block.Block;
import nl.kingdev.jcraft.engine.gameobj.GameObject;
import nl.kingdev.jcraft.engine.intefaces.IDestroyable;
import nl.kingdev.jcraft.engine.mesh.Mesh;
import nl.kingdev.jcraft.engine.utils.FastNoise;
import nl.kingdev.jcraft.utils.ArrayUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.*;

public class Chunk extends GameObject implements IDestroyable {

    private final int CHUNK_SIZE = 32;
    private final int CHUNK_HEIGHT = 4;


    private Map<Vector3i, Block> blocks = new HashMap<>();

    private static FastNoise fastNoise = new FastNoise((int) System.nanoTime());

    private Vector3i chunkPos;

    public Chunk(Vector3i chunkPos) {
        super(null);
        this.chunkPos = chunkPos;

        this.setPosition(new Vector3f(chunkPos.x*CHUNK_SIZE, chunkPos.y*CHUNK_HEIGHT, chunkPos.z*CHUNK_SIZE));


        fillChunk();
        buildMesh();
    }

    public void fillChunk() {

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {


                    float perlinX = (chunkPos.x * CHUNK_SIZE) + x;
                    float perlinZ = (chunkPos.z * CHUNK_SIZE) + z;


                    int perlin = (int) (fastNoise.GetPerlin(perlinX, perlinZ) * 100)/2;
                    Vector3i pos = new Vector3i(x, y+perlin, z);
                    blocks.put(pos, new Block(1, pos));

                }
            }
        }


    }

    public void buildMesh() {

        List<Float> verts = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();


        blocks.forEach((blockPos, block) -> {

            if (getBlockAt(blockPos.x, blockPos.y, blockPos.z + 1) == null) {
                //FRONT
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + 1f)); //t-left
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + -1f, blockPos.z + 1f)); //b-left
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z + 1f)); //b-right


                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z + 1f)); //b-right
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z + 1f)); //t-right
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + 1f)); //t-left


                textCoords.addAll(Arrays.asList(0f, 0f));
                textCoords.addAll(Arrays.asList(0f, 1f));
                textCoords.addAll(Arrays.asList(1f, 1f));
                textCoords.addAll(Arrays.asList(1f,0f));
                //END-FRONT
            }

            if (getBlockAt(blockPos.x, blockPos.y, blockPos.z - 1) == null) {

                //BACK
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + -1f)); //t-left
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + -1f, blockPos.z + -1f)); //b-left
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z + -1f)); //b-right


                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z + -1f)); //b-right
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z + -1f)); //t-right
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + -1f)); //t-left


                textCoords.addAll(Arrays.asList(0f, 0f));
                textCoords.addAll(Arrays.asList(0f, 1f));
                textCoords.addAll(Arrays.asList(1f, 1f));
                textCoords.addAll(Arrays.asList(1f,0f));
                //END-BACK
            }
            if (getBlockAt(blockPos.x - 1, blockPos.y, blockPos.z) == null) {
                //LEFT
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + -1f)); //t-left
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y - 1f, blockPos.z + -1f)); //b-left
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y - 1f, blockPos.z + 1f)); //f-b-left


                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y - 1f, blockPos.z + 1f)); //f-b-left
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + 1f)); //f-t-left
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z - 1f)); //b-t-left

                textCoords.addAll(Arrays.asList(0f, 0f));
                textCoords.addAll(Arrays.asList(0f, 1f));
                textCoords.addAll(Arrays.asList(1f, 1f));
                textCoords.addAll(Arrays.asList(1f,0f));
                //END-LEFT
            }
            if (getBlockAt(blockPos.x + 1, blockPos.y, blockPos.z) == null) {
                //RIGHT
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z + -1f)); //t-right
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y - 1f, blockPos.z + -1f)); //b-right
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y - 1f, blockPos.z + 1f)); //f-b-right


                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y - 1f, blockPos.z + 1f)); //f-b-right
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z + 1f)); //f-t-right
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z - 1f)); //b-t-right

                textCoords.addAll(Arrays.asList(0f, 0f));
                textCoords.addAll(Arrays.asList(0f, 1f));
                textCoords.addAll(Arrays.asList(1f, 1f));
                textCoords.addAll(Arrays.asList(1f,0f));
                //END-RIGHT
            }
            if (getBlockAt(blockPos.x, blockPos.y + 1, blockPos.z) == null) {
                //TOP
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + -1f)); //back-b-left
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + 1f)); //back-f-left
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z + 1f)); //back-f-right


                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z + 1f)); //back-f-right
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z - 1f)); //back-b-right
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + -1f)); //back-b-left

                textCoords.addAll(Arrays.asList(0f, 0f));
                textCoords.addAll(Arrays.asList(0f, 1f));
                textCoords.addAll(Arrays.asList(1f, 1f));
                textCoords.addAll(Arrays.asList(1f,0f));
                //END-TOP
            }
            if (getBlockAt(blockPos.x, blockPos.y - 1, blockPos.z) == null) {
                //BOTTOM
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + -1f, blockPos.z + -1f)); //back-b-left
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + -1f, blockPos.z + 1f)); //back-f-left
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z + 1f)); //back-f-right


                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z + 1f)); //back-f-right
                verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z - 1f)); //back-b-right
                verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + -1f, blockPos.z + -1f)); //back-b-left

                textCoords.addAll(Arrays.asList(0f, 0f));
                textCoords.addAll(Arrays.asList(0f, 1f));
                textCoords.addAll(Arrays.asList(1f, 1f));
                textCoords.addAll(Arrays.asList(1f,0f));
                //END-BOTTOM
            }
        });



        for (int i = 0; i < verts.size() / 3; i++) {
            indices.add(i);
        }

        setMesh(new Mesh(ArrayUtils.floatArray(verts), ArrayUtils.intArray(indices), ArrayUtils.floatArray(textCoords)));


        getMesh().setTexture(Textures.GRASS);

    }


    public Block getBlockAt(int x, int y, int z) {
        Vector3i pos = new Vector3i(x, y, z);
        if (blocks.containsKey(pos)) {
            return blocks.get(pos);
        }
        return null;
    }

    @Override
    public void destroy() {
        if (getMesh() != null) {
            getMesh().destroy();
        }
    }
}
