
package rge;

/**
 *
 * @author Rolf
 */
public interface Scene {

    /**
     * Renders this scene's scenegraph
     */
    public void renderScene();

    /**
     * Runs update on the scenegraph of this scene
     * @param deltaTime the time since last update
     */
    public void updateScene(double deltaTime);

    /**
     * Setup the scenegraph of this scene
     */
    public void setupScene();
}
