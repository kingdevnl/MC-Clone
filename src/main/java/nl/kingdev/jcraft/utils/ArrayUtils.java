package nl.kingdev.jcraft.utils;

import java.util.List;

public class ArrayUtils {

    public static float[] floatArray(List<Float> list) {
        float[] floats = new float[list.size()];

        for (int i = 0; i < list.size(); i++) {
            floats[i] = list.get(i);
        }

        return floats;
    }

    public static int[] intArray(List<Integer> list) {
        int[] ints = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            ints[i] = list.get(i);
        }

        return ints;
    }

}
