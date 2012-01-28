/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.textures;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import dk.lystrup.lagl.Display;
import java.nio.IntBuffer;

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

    public void bind() {
        if (!loaded) {
            load();
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
    }

    public void unbind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    private void load() {
        Bitmap bmp = loadImage();

        IntBuffer textureIds = IntBuffer.allocate(1);
        GLES20.glGenTextures(textureId, textureIds);

        textureId = textureIds.get(0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

        loaded = true;
    }

    private Bitmap loadImage() {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        Bitmap bmp = BitmapFactory.decodeResource(Display.singleton().getContext().getResources(), resourceId, opts);

        return bmp;
    }
}
