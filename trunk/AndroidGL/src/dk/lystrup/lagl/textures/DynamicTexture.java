/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.textures;

import android.opengl.GLES20;
import android.util.Log;
import dk.lystrup.lagl.LAGLUtil;

/**
 * A DynamicTexture represents something where the texture id is allowed to change,
 * but they cannot be used before they have been initialized with an id.
 * @author Rolf
 */
public class DynamicTexture implements Texture {

    private int textureId;
    private boolean initialized;
    
    public DynamicTexture() {
        this(-1);
    }

    public DynamicTexture(int textureId) {
        initialized = textureId != -1;
        this.textureId = textureId;
    }
    
    public void bind() {
        if(!initialized) {
            Log.wtf("LAGL", "Unable to use a dynamic texture before initializing it.");
            System.exit(-1);
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        LAGLUtil.checkGlError("glBindTexture");
    }

    public void unbind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        LAGLUtil.checkGlError("glBindTexture");
    }
    
    public void setTextureId(int textureId) {
        this.textureId = textureId;
        initialized = true;
    }

}
