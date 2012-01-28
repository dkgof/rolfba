/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.textures;

import android.opengl.GLES20;
import android.util.Log;

/**
 * A DynamicTexture represents something where the texture id is allowed to change,
 * but they cannot be used before they have been initialized with an id.
 * @author Rolf
 */
public class DynamicTexture implements Texture {

    private int textureId;
    private boolean initialized;
    
    public DynamicTexture() {
        initialized = false;
        textureId = -1;
    }
    
    public void bind() {
        if(!initialized) {
            Log.wtf("AndroidGL", "Unable to use a dynamic texture before initializing it.");
            System.exit(-1);
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
    }

    public void unbind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }
    
    public void setTextureId(int textureId) {
        this.textureId = textureId;
        initialized = true;
    }

}
