/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.nodes;

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
        //Create a byte buffer in native memory and wrap it into a float buffer
        ByteBuffer vbb = ByteBuffer.allocateDirect(data.getVertices().length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(data.getVertices());
        vertexBuffer.flip();

        //Create a byte buffer in native memory and wrap it into a float buffer
        ByteBuffer tbb = ByteBuffer.allocateDirect(data.getTexturecoords().length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texturecoordsBuffer = tbb.asFloatBuffer();
        texturecoordsBuffer.put(data.getTexturecoords());
        texturecoordsBuffer.flip();

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
        //Enable the needed vertex and texture arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        
        //Setup pointers to the used arrays
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texturecoordsBuffer);

        //Draw the elements of the model
        gl.glDrawElements(GL10.GL_TRIANGLES, indicesCount, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        //Disable vertex and texture array support
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
}
