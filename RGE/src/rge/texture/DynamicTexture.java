package rge.texture;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Rolf
 */
public class DynamicTexture implements Texture {

    private int textureId;

    public DynamicTexture() {
        textureId = -1;
    }

    public DynamicTexture(int id) {
        textureId = id;
    }

    public void setTextureId(int id) {
        textureId = id;
    }

    @Override
    public void bind() {
        if(textureId == -1) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to use DynamicTexture uninitialized");
            System.exit(-1);
        }
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    }
}
