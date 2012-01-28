/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.actuators;

import dk.lystrup.lagl.nodes.Node;

/**
 * An Actuator acts opun a Node in some fashion, and is called each frame
 * When called the deltatime in seconds since last frame is supplied as input.
 *
 * @author Rolf
 */
public interface Actuator {

    /**
     * This method runs this actuator on the given Node
     * @param node the Node to update
     * @param deltaTime the time since last frame in seconds
     */
    public void update(Node node, float deltaTime);
}
