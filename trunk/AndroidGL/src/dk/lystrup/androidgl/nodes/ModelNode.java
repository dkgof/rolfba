/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.nodes;

import static android.opengl.GLES10.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 * This Node renders a 3d-model described by a ModelData object.
 * @author Rolf
 */
public class ModelNode extends AbstractNode {
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer texturecoordsBuffer;
    private final ShortBuffer indexBuffer;

    private int indicesCount;

    /**
     * Create a new ModelNode from the given ModelData
     * @param data the ModelData to use for this model
     */
    public ModelNode(ModelData data) {
        if(data.getVertices() != null) {
            //Create a byte buffer in native memory and wrap it into a float buffer
            ByteBuffer vbb = ByteBuffer.allocateDirect(data.getVertices().length * 4);
            vbb.order(ByteOrder.nativeOrder());
            vertexBuffer = vbb.asFloatBuffer();
            vertexBuffer.put(data.getVertices());
            vertexBuffer.flip();
        } else {
            vertexBuffer = null;
        }

        if(data.getTexturecoords() != null) {
            //Create a byte buffer in native memory and wrap it into a float buffer
            ByteBuffer tbb = ByteBuffer.allocateDirect(data.getTexturecoords().length * 4);
            tbb.order(ByteOrder.nativeOrder());
            texturecoordsBuffer = tbb.asFloatBuffer();
            texturecoordsBuffer.put(data.getTexturecoords());
            texturecoordsBuffer.flip();
        } else {
            texturecoordsBuffer = null;
        }

        //Create a byte buffer in native memory and wrap it into a short buffer
        ByteBuffer ibb = ByteBuffer.allocateDirect(data.getIndices().length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(data.getIndices());
        indexBuffer.flip();

        //Remember the count of indices for quick access during rendering
        indicesCount = data.getIndices().length;
    }

    @Override
    public void render(GL10 gl) {
        if(vertexBuffer != null) {
            //Enable the needed vertex array
            glEnableClientState(GL_VERTEX_ARRAY);
            //Setup pointer to the used array
            glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
        }
        
        if(texturecoordsBuffer != null) {
            //Enable the needed texture array
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            //Setup pointer to the used array
            glTexCoordPointer(2, GL_FLOAT, 0, texturecoordsBuffer);
        }

        //Draw the elements of the model
        glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_SHORT, indexBuffer);

        //Disable vertex and texture array support
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    }
}
