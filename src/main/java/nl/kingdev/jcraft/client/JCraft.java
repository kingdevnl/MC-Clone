package nl.kingdev.jcraft.client;

import lombok.Getter;
import nl.kingdev.jcraft.engine.mesh.Mesh;
import nl.kingdev.jcraft.engine.shader.ShaderProgram;
import nl.kingdev.jcraft.engine.utils.MatrixUtils;
import nl.kingdev.jcraft.engine.window.Window;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import javax.xml.transform.Transformer;

@Getter
public class JCraft {

    private Window window;

    public static void main(String[] args) {
        new JCraft().boot();
    }

    float color = 0;
    boolean up = true;

    private void boot() {
        window = new Window(1080, 720, "JCraft");

        window.setVisible(true);
        MatrixUtils.setup(window);

        ShaderProgram shaderProgram = new ShaderProgram("base/vertex.glsl", "base/fragment.glsl");

        shaderProgram.bind();
        shaderProgram.createUniform("projectionMatrix");

        shaderProgram.setMatrixUniform("projectionMatrix", MatrixUtils.projectionMatrix);
        shaderProgram.unbind();

        Mesh mesh = new Mesh(new float[]{
                -1, -1, -2f,
                1, -1, -2f,
                0, 1, -2f,
        }, new int[]{0, 1, 2});

        while (!window.isCloseRequested()) {
            window.clear();

            shaderProgram.bind();
            mesh.render();
            shaderProgram.unbind();

            window.update();
        }
        shaderProgram.destroy();
        mesh.destroy();
    }
}
