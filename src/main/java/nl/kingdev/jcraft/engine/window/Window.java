package nl.kingdev.jcraft.engine.window;

import lombok.Getter;
import nl.kingdev.jcraft.utils.Logger;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

@Getter
public class Window {
    private long handle;

    private int width, height;

    private static Logger logger = new Logger(Window.class);

    private List<Integer> keysDown = new ArrayList<>();

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;

        logger.info("Creating window " + title);
        if (!glfwInit()) {
            logger.error("Failed to init glfw!");
            System.exit(1);
        }


        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        handle = glfwCreateWindow(width, height, title, 0, 0);

        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                if (!keysDown.contains(key)) {
                    keysDown.add(key);
                }
            }
            if (action == GLFW_RELEASE) {
                if (keysDown.contains(key)) {
                    keysDown.remove((Object) key);
                }
            }
        });
    }

    public void setVisible(boolean v) {
        if (v) {
            glfwShowWindow(handle);
            glfwMakeContextCurrent(handle);
            GL.createCapabilities();

            return;
        }
        glfwHideWindow(handle);
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(handle);
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(handle);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    public boolean isKeyDown(int k) {
        return keysDown.contains(k);
    }
}
