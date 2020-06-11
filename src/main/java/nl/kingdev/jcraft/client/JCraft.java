package nl.kingdev.jcraft.client;

import lombok.Getter;
import nl.kingdev.jcraft.client.world.World;
import nl.kingdev.jcraft.client.world.chunk.Chunk;
import nl.kingdev.jcraft.engine.camera.Camera;
import nl.kingdev.jcraft.engine.shader.ShaderProgram;
import nl.kingdev.jcraft.engine.utils.MatrixUtils;
import nl.kingdev.jcraft.engine.window.Window;

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

    public World world = new World();


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


        world.build();


//        camera.setPosition(0,0,-5);


//        fastNoise.SetNoiseType(FastNoise.NoiseType.Cubic);

//        int size = 40;
//        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                for (int z = 0; z < 2; z++) {
//                    GameObject obj = new GameObject(mesh);
//                    int perlin = (int) (fastNoise.GetNoise(x, y) * 100);
//
//                    obj.setPosition(new Vector3f(-1+x, z+perlin, -5+y));
//                    gameObjects.add(obj);
//                }
//
//
//            }
//        }


        camera.setPosition(5, 4, 34);


        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        while (!window.isCloseRequested()) {
            window.clear();

            glEnable(GL_DEPTH_TEST);
            camera.update(window);
            shaderProgram.bind();

            for (Chunk chunk : world.getChunks()) {
                shaderProgram.setMatrixUniform("worldViewMatrix", MatrixUtils.getWorldViewMatrix(chunk, camera));

                System.out.println("RenderChunk: "+chunk);
                if (chunk.getMesh() != null) {
                    chunk.getMesh().render();
                }
            }
//            for(GameObject gameObject : gameObjects) {
//
//                shaderProgram.setMatrixUniform("worldViewMatrix", MatrixUtils.getWorldViewMatrix(gameObject, camera));
//                gameObject.getMesh().render();
//
//            }
            shaderProgram.unbind();

            glDisable(GL_DEPTH_TEST);


            window.update();
        }
        world.destroy();
        shaderProgram.destroy();


    }
}
