/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.textures;

/**
 * A texture represents in image in OpenGL and is used for adding images to models
 * @author Rolf
 */
public interface Texture {
    /**
     * Bind this texture as the current texture on the currently active texture unit
     */
    public void bind();

    /**
     * Unbind the currently active texture on the active texture unit
     */
    public void unbind();
}
