/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl;

import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public interface Scene {

    /**
     * Initialize the scene so its ready for rendering
     */
    public void init();

    /**
     * Update the scene
     * @param deltaTime the time since last update run
     */
    public void update(float deltaTime);

    /**
     * Render the scene
     * @param gl
     */
    public void render(GL10 gl);
    
    
}
