/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.androidgl.textures;

import static android.opengl.GLES10.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import dk.lystrup.androidgl.Display;
import java.nio.IntBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 * StaticTexture represents a texture from a static image, ie. something that
 * will not change over time. For example a bitmap texture.
 * @author Rolf
 */
public class StaticTexture implements Texture {

    private int textureId;
    private boolean loaded;
    private final int resourceId;

    public StaticTexture(int resourceId) {
        this.resourceId = resourceId;
        loaded = false;
    }

    public void bind(GL10 gl) {
        if (!loaded) {
            load(gl);
        }
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void unbind(GL10 gl) {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void load(GL10 gl) {
        Bitmap bmp = loadImage();

        IntBuffer textureIds = IntBuffer.allocate(1);
        glGenTextures(textureId, textureIds);

        textureId = textureIds.get(0);

        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bmp, 0);

        loaded = true;
    }

    private Bitmap loadImage() {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        Bitmap bmp = BitmapFactory.decodeResource(Display.singleton().getContext().getResources(), resourceId, opts);

        return bmp;
    }
}
