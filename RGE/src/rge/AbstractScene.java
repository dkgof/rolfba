
package rge;

import rge.nodes.EmptyNode;
import rge.nodes.Node;

/**
 *
 * @author Rolf
 */
public abstract class AbstractScene implements Scene {
    //The root node of this scene's scenegraph
    protected Node root = new EmptyNode();

    @Override
    public void renderScene() {
        root.recursiveRender();
    }

    @Override
    public void updateScene(double deltaTime) {
        root.recursiveUpdate(deltaTime);
    }
}
