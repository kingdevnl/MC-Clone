package nl.kingdev.jcraft.engine.mesh;

import lombok.AccessLevel;
import lombok.Getter;
import nl.kingdev.jcraft.engine.intefaces.IDestroyable;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh implements IDestroyable {

    private final int vao, vbo, idxVbo, vertexCount;


    public Mesh(float[] positions, int[] indices) {

        FloatBuffer posBuffer = MemoryUtil.memAllocFloat(positions.length);
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);

        posBuffer.put(positions).flip();
        indicesBuffer.put(indices).flip();

        vertexCount = positions.length/3;


        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //Positions
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        //End Positions

        //Indices
        idxVbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        //End Indices
        glBindVertexArray(0);


        MemoryUtil.memFree(posBuffer);
        MemoryUtil.memFree(indicesBuffer);
    }

    public void render() {

        glBindVertexArray(vbo);
        glEnableVertexAttribArray(0);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
//        glDrawArrays(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

    }

    @Override
    public void destroy() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(idxVbo);
        glDeleteVertexArrays(vao);
    }


}
