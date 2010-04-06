
package rge.actuators;

import rge.nodes.Node;

/**
 *
 * @author Rolf
 */
public interface Actuator {
    public void update(Node node, double deltaTime);
}
