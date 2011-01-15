/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.textures;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.khronos.opengles.GL10;

/**
 *
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
            Logger.getLogger(DynamicTexture.class.getName()).log(Level.SEVERE, "Unable to use a dynamic texture before initializing it.");
            System.exit(-1);
        }
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
    }

    public void unbind(GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
    }
    
    public void setTextureId(int textureId) {
        this.textureId = textureId;
        initialized = true;
    }

}
