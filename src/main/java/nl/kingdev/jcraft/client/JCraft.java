package nl.kingdev.jcraft.client;

import lombok.Getter;
import nl.kingdev.jcraft.engine.camera.Camera;
import nl.kingdev.jcraft.engine.gameobj.GameObject;
import nl.kingdev.jcraft.engine.mesh.Mesh;
import nl.kingdev.jcraft.engine.shader.ShaderProgram;
import nl.kingdev.jcraft.engine.utils.MatrixUtils;
import nl.kingdev.jcraft.engine.window.Window;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import javax.xml.transform.Transformer;

import static org.lwjgl.opengl.GL11.*;

@Getter
public class JCraft {

    private Window window;

    public static void main(String[] args) {
        new JCraft().boot();
    }

    float scale = 1;
    boolean up = true;


    public Camera camera = new Camera();

    private void boot() {
        window = new Window(1080, 720, "JCraft");

        window.setVisible(true);
        MatrixUtils.setup(window);

        ShaderProgram shaderProgram = new ShaderProgram("base/vertex.glsl", "base/fragment.glsl");

        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldViewMatrix");

        shaderProgram.bind();
        shaderProgram.setMatrixUniform("projectionMatrix", MatrixUtils.projectionMatrix);
        shaderProgram.unbind();

        Mesh mesh = new Mesh(new float[]{
                -1, -1, 0,
                1, -1, 0,
                0, 1, 0,
        }, new int[]{0, 1, 2});

        GameObject gameObject = new GameObject(mesh);
        gameObject.setPosition(new Vector3f(0, 0, -5));
        while (!window.isCloseRequested()) {
            window.clear();

            glEnable(GL_DEPTH_TEST);

            shaderProgram.bind();
            camera.update(window);

            shaderProgram.setMatrixUniform("worldViewMatrix", MatrixUtils.getWorldViewMatrix(gameObject, camera));
            mesh.render();
            shaderProgram.unbind();



            gameObject.getRotation().x+=.5f;
            gameObject.getRotation().y+=1f;


            glDisable(GL_DEPTH_TEST);


            gameObject.setScale(scale);
            window.update();
        }
        shaderProgram.destroy();
        mesh.destroy();
    }
}
