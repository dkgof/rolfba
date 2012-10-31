/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.nodes;

/**
 * The EmptyNode models a node that is invisible, and is great for using as a
 * building block in the scene graph when something has to be tied together.
 * 
 * Children added to the EmptyNode will be rendered though, and any transformations
 * applied to the EmptyNode works as normal.
 * @author Rolf
 */
public class EmptyNode extends AbstractNode {

    public EmptyNode() {
        shader = null;
    }
    
    @Override
    public void render() {
        //EmptyNode does nothing on render
    }

}
