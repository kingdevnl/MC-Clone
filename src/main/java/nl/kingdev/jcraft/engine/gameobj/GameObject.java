package nl.kingdev.jcraft.engine.gameobj;

import lombok.Getter;
import lombok.Setter;
import nl.kingdev.jcraft.engine.mesh.Mesh;
import org.joml.Vector3f;

@Getter
@Setter
public class GameObject {

    private Mesh mesh;
    private Vector3f position, rotation;
    private float scale = 1;

    public GameObject(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }
}
