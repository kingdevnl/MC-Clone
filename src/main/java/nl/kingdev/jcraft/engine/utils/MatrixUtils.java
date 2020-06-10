package nl.kingdev.jcraft.engine.utils;

import nl.kingdev.jcraft.engine.camera.Camera;
import nl.kingdev.jcraft.engine.gameobj.GameObject;
import nl.kingdev.jcraft.engine.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixUtils {

    public static float FOV = (float) Math.toRadians(60.0f);
    public static float Z_NEAR = 0.01f;
    public static float Z_FAR = 1000.0f;

    public static Matrix4f projectionMatrix = new Matrix4f();
    public static Matrix4f worldMatrix = new Matrix4f();
    public static Matrix4f viewMatrix = new Matrix4f();


    public static void setup(Window window) {
        float apsect = (float) window.getWidth()/window.getHeight();
        projectionMatrix.setPerspective(FOV, apsect, Z_NEAR, Z_FAR);

    }
    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();

        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }
    public static Matrix4f getWorldViewMatrix(GameObject gameObject, Camera camera) {
        Vector3f rotation = gameObject.getRotation();
        worldMatrix.set(getViewMatrix(camera)).translate(gameObject.getPosition()).
                rotateX((float) Math.toRadians(-rotation.x)).
                rotateY((float) Math.toRadians(-rotation.y)).
                rotateZ((float) Math.toRadians(-rotation.z)).
                scale(gameObject.getScale());
        return worldMatrix;
    }

}
