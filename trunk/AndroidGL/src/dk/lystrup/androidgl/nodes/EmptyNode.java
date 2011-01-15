/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.nodes;

import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public class EmptyNode extends AbstractNode {

    @Override
    public void render(GL10 gl) {
        //EmptyNode does nothing on render
    }

}
