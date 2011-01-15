/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl;

import dk.lystrup.androidgl.nodes.EmptyNode;
import dk.lystrup.androidgl.nodes.Node;
import javax.microedition.khronos.opengles.GL10;

/**
 * Implementing class that handles the basic work of the Scene interface
 * @author Rolf
 */
public abstract class AbstractScene implements Scene {

    protected final Node root;
    
    private boolean initialized;
    
    public AbstractScene() {
        root = new EmptyNode();
        initialized = false;
    }
    
    public final void init() {
        if(!initialized) {
            customInit();
            initialized = true;
        }
    }

    public final void update(float deltaTime) {
        root.recursiveUpdate(deltaTime);
    }
    
    public final void render(GL10 gl) {
        root.recursiveRender(gl);
    }

    protected abstract void customInit();
    
}
