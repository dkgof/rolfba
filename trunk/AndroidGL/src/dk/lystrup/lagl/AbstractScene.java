/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl;

import dk.lystrup.lagl.nodes.EmptyNode;
import dk.lystrup.lagl.nodes.Node;

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
    
    public final void render() {
        root.recursiveRender();
    }

    protected abstract void customInit();
    
}
