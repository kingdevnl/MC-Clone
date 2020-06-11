package nl.kingdev.jcraft.engine.shader;


import nl.kingdev.jcraft.utils.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private static final Logger logger = new Logger(ShaderProgram.class);

    private int program;

    private List<Shader> shaders = new ArrayList<>();


    private HashMap<String, Integer> uniformLocations = new HashMap<>();


    public ShaderProgram(String vertexFile, String fragmentFile) {
        shaders.add(new Shader(vertexFile, GL_VERTEX_SHADER));
        shaders.add(new Shader(fragmentFile, GL_FRAGMENT_SHADER));

        logger.info(String.format("Loading shader %s %s", vertexFile, fragmentFile));

        loadShaderProgram();
    }

    public void createUniform(String name) {
        int loc = glGetUniformLocation(program, name);
        if (loc < 0) {
            logger.error("Failed to find uniform " + name);
            return;
        }
        uniformLocations.put(name, loc);
    }

    public void setMatrixUniform(String name, Matrix4f matrix) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniformLocations.get(name), false, matrix.get(stack.mallocFloat(16)));
        }

    }

    public void setVectorUniform(String name, Vector3f value) {
        glUniform3f(uniformLocations.get(name), value.x, value.y, value.z);
    }
    public void setUniform(String uniformName, int value) {
        glUniform1i(uniformLocations.get(uniformName), value);
    }

    private void loadShaderProgram() {
        program = glCreateProgram();
        shaders.forEach(shader -> glAttachShader(program, createShader(shader)));
        glLinkProgram(program);
        shaders.forEach(shader -> {
            glDetachShader(program, shader.id);
            glDeleteShader(shader.id);
        });
    }

    private int createShader(Shader shader) {
        int shaderID = shader.id = glCreateShader(shader.type);
        StringBuilder shaderSource = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/assets/shaders/" + shader.file)))) {
            String line;
            shaderSource.append("#version 330 core").append("\r\n");
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        glShaderSource(shaderID, shaderSource.toString());
        glCompileShader(shaderID);
        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            logger.error("Failed to compile shader " + glGetShaderInfoLog(shaderID));
        }

        return shaderID;

    }


    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void destroy() {
        glDeleteProgram(program);
        shaders.clear();
    }


    public int getProgramID() {
        return program;
    }

    public Logger getLogger() {
        return logger;
    }



    private static class Shader {
        protected String file;
        protected int id, type;

        public Shader(String file, int type) {
            this.file = file;
            this.type = type;
        }
    }


}
