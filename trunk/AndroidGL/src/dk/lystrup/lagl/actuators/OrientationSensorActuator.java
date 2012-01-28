/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.actuators;

import dk.lystrup.lagl.SensorCore;
import dk.lystrup.lagl.math.Quaternion;
import dk.lystrup.lagl.nodes.Node;

/**
 *
 * @author Rolf
 */
public class OrientationSensorActuator implements Actuator {

    public void update(Node node, float deltaTime) {
        Quaternion phoneRotation = SensorCore.singleton().getCurrentRotation();
        node.setRotation(phoneRotation);
    }
    
}
