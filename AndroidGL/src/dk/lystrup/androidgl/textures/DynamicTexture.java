/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.textures;

import static android.opengl.GLES10.GL_TEXTURE_2D;
import static android.opengl.GLES10.glBindTexture;
import android.util.Log;
import javax.microedition.khronos.opengles.GL10;

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
    
    public void bind(GL10 gl) {
        if(!initialized) {
            Log.wtf("AndroidGL", "Unable to use a dynamic texture before initializing it.");
            System.exit(-1);
        }
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void unbind(GL10 gl) {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    public void setTextureId(int textureId) {
        this.textureId = textureId;
        initialized = true;
    }

}
