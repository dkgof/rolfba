/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.textures;

import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public interface Texture {
    public void bind(GL10 gl);

    public void unbind(GL10 gl);
}
