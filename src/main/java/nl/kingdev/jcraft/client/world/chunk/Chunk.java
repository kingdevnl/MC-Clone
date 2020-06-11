package nl.kingdev.jcraft.client.world.chunk;

import nl.kingdev.jcraft.client.world.block.Block;
import nl.kingdev.jcraft.engine.gameobj.GameObject;
import nl.kingdev.jcraft.engine.intefaces.IDestroyable;
import nl.kingdev.jcraft.engine.mesh.Mesh;
import nl.kingdev.jcraft.utils.ArrayUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.lang.reflect.Array;
import java.util.*;

public class Chunk extends GameObject implements IDestroyable {

    private final int CHUNK_SIZE = 16;
    private final int CHUNK_HEIGHT = 4;


    private Map<Vector3i, Block> blocks = new HashMap<>();


    public Chunk(Vector3i chunkPos) {
        super(null);

        this.setPosition(new Vector3f(chunkPos.x, chunkPos.y, chunkPos.z));


        fillChunk();
        buildMesh();
    }

    public void fillChunk() {

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    Vector3i pos = new Vector3i(x, y, z);

                    blocks.put(pos, new Block(1, pos));

                }
            }
        }


    }

    public void buildMesh() {

        List<Float> verts = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    Vector3i blockPos = new Vector3i(x, y, z);

                    if (getBlockAt(blockPos.x, blockPos.y, blockPos.z + 1) == null) {
                        //FRONT
                        verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + 1f)); //t-left
                        verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + -1f, blockPos.z + 1f)); //b-left
                        verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z + 1f)); //b-right


                        verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + -1f, blockPos.z + 1f)); //b-right
                        verts.addAll(Arrays.asList(blockPos.x + 1f, blockPos.y + 1f, blockPos.z + 1f)); //t-right
                        verts.addAll(Arrays.asList(blockPos.x + -1f, blockPos.y + 1f, blockPos.z + 1f)); //t-left
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
                        //END-BOTTOM
                    }
                }
            }
        }
        System.out.println("done");

        for (int i = 0; i < verts.size() / 3; i++) {
            indices.add(i);
        }

        setMesh(new Mesh(ArrayUtils.floatArray(verts), ArrayUtils.intArray(indices)));

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
