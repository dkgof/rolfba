/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl;

/**
 * A Scene describes a 3d environment with stuff in it, in AndroidGL this is
 * represented by a scene graph of nodes in a tree like structure
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
     */
    public void render();
    
    
}
