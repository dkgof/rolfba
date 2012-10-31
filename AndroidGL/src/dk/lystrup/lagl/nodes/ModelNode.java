/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.nodes;

import android.opengl.GLES20;
import dk.lystrup.lagl.LAGLUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

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
            ByteBuffer vbb = ByteBuffer.allocateDirect(data.getVertices().length * (Float.SIZE / 8));
            vbb.order(ByteOrder.nativeOrder());
            vertexBuffer = vbb.asFloatBuffer();
            vertexBuffer.put(data.getVertices());
            vertexBuffer.flip();
        } else {
            vertexBuffer = null;
        }

        if(data.getTexturecoords() != null) {
            //Create a byte buffer in native memory and wrap it into a float buffer
            ByteBuffer tbb = ByteBuffer.allocateDirect(data.getTexturecoords().length * (Float.SIZE / 8));
            tbb.order(ByteOrder.nativeOrder());
            texturecoordsBuffer = tbb.asFloatBuffer();
            texturecoordsBuffer.put(data.getTexturecoords());
            texturecoordsBuffer.flip();
        } else {
            texturecoordsBuffer = null;
        }

        //Create a byte buffer in native memory and wrap it into a short buffer
        ByteBuffer ibb = ByteBuffer.allocateDirect(data.getIndices().length * (Short.SIZE / 8));
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(data.getIndices());
        indexBuffer.flip();

        //Remember the count of indices for quick access during rendering
        indicesCount = data.getIndices().length;
    }

    @Override
    public void render() {
        int vertexAttribPosition = shader.getVertexAttrib();
        int textureCoordAttribPosition = shader.getTexCoordAttrib();
        
        if(vertexBuffer != null) {
            GLES20.glVertexAttribPointer(vertexAttribPosition, 3, GLES20.GL_FLOAT, false, 3 * (Float.SIZE / 8), vertexBuffer);
            LAGLUtil.checkGlError("glVertexAttribPointer - vertexBuffer");
            GLES20.glEnableVertexAttribArray(vertexAttribPosition);
            LAGLUtil.checkGlError("glEnableVertexAttribArray - vertexBuffer");
        }
        
        if(texturecoordsBuffer != null) {
            GLES20.glVertexAttribPointer(textureCoordAttribPosition, 2, GLES20.GL_FLOAT, false, 2 * (Float.SIZE / 8), texturecoordsBuffer);
            LAGLUtil.checkGlError("glVertexAttribPointer - texturecoordsBuffer");
            GLES20.glEnableVertexAttribArray(textureCoordAttribPosition);
            LAGLUtil.checkGlError("glEnableVertexAttribArray - texturecoordsBuffer");
        }

        //Draw the elements of the model
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indicesCount, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
        LAGLUtil.checkGlError("glDrawElements");

        //Disable vertex and texture array support
        GLES20.glDisableVertexAttribArray(vertexAttribPosition);
        LAGLUtil.checkGlError("glDisableVertexAttribArray - vertexBuffer");
        GLES20.glDisableVertexAttribArray(textureCoordAttribPosition);
        LAGLUtil.checkGlError("glDisableVertexAttribArray - texturecoordsBuffer");
    }
}
