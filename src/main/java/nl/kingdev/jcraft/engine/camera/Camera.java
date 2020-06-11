package nl.kingdev.jcraft.engine.camera;


import javax.vecmath.Vector2f;
import lombok.Setter;
import nl.kingdev.jcraft.engine.utils.MouseUtils;
import nl.kingdev.jcraft.engine.window.Window;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private static final float MOUSE_SENSITIVITY = .3f;

    private final Vector3f position;

    private final Vector3f rotation;

    @Setter
   private float cameraSpeed = 0.5f;

    @Setter
    private float rotationSpeed = cameraSpeed*12;

    private MouseUtils mouseUtils = new MouseUtils();

    public Camera(Window window) {
        position = new Vector3f();
        rotation = new Vector3f();
        mouseUtils.init(window);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public void update(Window window) {
        if(window.isKeyDown(GLFW.GLFW_KEY_W)) {
            movePosition(0,0, -cameraSpeed);

        }
        if(window.isKeyDown(GLFW.GLFW_KEY_S)) {
            movePosition(0,0, cameraSpeed);
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_A)) {
            movePosition(-cameraSpeed, 0, 0);
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_D)) {
            movePosition(cameraSpeed, 0, 0);
        }

        if(window.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
            moveRotation(0, -rotationSpeed, 0);
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
            moveRotation(0, rotationSpeed, 0);
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
            moveRotation(rotationSpeed, 0,0 );
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_UP)) {
            moveRotation(-rotationSpeed, 0,0 );
        }

        if(window.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            movePosition(0, cameraSpeed, 0);
        }
        if(window.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            movePosition(0, -cameraSpeed, 0);
        }

        mouseUtils.input(window);
        if (mouseUtils.isRightButtonPressed()) {
            Vector2f rotVec = mouseUtils.getDisplVec();
            moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }
}
