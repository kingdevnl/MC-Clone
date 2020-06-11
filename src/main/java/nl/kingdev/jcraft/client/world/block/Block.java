package nl.kingdev.jcraft.client.world.block;

import lombok.Getter;
import org.joml.Vector3i;

@Getter
public class Block {

    private int id;
    private Vector3i blockPos;

    public Block(int id, Vector3i blockPos) {
        this.id = id;
        this.blockPos =blockPos;
    }
}
