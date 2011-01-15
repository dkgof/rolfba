/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl;

import dk.lystrup.androidgl.nodes.EmptyNode;
import dk.lystrup.androidgl.nodes.Node;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public abstract class AbstractScene implements Scene {

    protected final Node root;
    
    public AbstractScene() {
        root = new EmptyNode();
    }
    
    public final void init() {
        customInit();
    }

    public final void update(float deltaTime) {
        root.recursiveUpdate(deltaTime);
    }
    
    public final void render(GL10 gl) {
        root.recursiveRender(gl);
    }

    protected abstract void customInit();
    
}
