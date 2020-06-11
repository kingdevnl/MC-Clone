package nl.kingdev.jcraft.engine.mesh;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.kingdev.jcraft.engine.intefaces.IDestroyable;
import nl.kingdev.jcraft.engine.texture.Texture;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh implements IDestroyable {

    private final int vao, vbo, idxVbo, textCoordsVbo, vertexCount;


    @Getter @Setter
    private Texture texture;

    public Mesh(float[] positions, int[] indices, float[] textCoords) {


        FloatBuffer posBuffer = MemoryUtil.memAllocFloat(positions.length);
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        FloatBuffer textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);

        posBuffer.put(positions).flip();
        indicesBuffer.put(indices).flip();
        textCoordsBuffer.put(textCoords).flip();
        vertexCount = indices.length;


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

        textCoordsVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textCoordsVbo);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        glBindVertexArray(0);


        MemoryUtil.memFree(posBuffer);
        MemoryUtil.memFree(indicesBuffer);
        MemoryUtil.memFree(textCoordsBuffer);
    }

    public void render() {

        if(getTexture() != null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, getTexture().getTextureID());
        }

        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

    }

    @Override
    public void destroy() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(idxVbo);
        glDeleteBuffers(textCoordsVbo);
        glDeleteVertexArrays(vao);

    }


}
