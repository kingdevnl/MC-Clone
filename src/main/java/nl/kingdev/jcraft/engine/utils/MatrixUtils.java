package nl.kingdev.jcraft.engine.utils;

import nl.kingdev.jcraft.engine.window.Window;
import org.joml.Matrix4f;

public class MatrixUtils {

    public static float FOV = (float) Math.toRadians(60.0f);
    public static float Z_NEAR = 0.01f;
    public static float Z_FAR = 1000.0f;

    public static Matrix4f projectionMatrix = new Matrix4f();


    public static void setup(Window window) {
        float apsect = (float) window.getWidth()/window.getHeight();
        projectionMatrix.setPerspective(FOV, apsect, Z_NEAR, Z_FAR);

    }

}
