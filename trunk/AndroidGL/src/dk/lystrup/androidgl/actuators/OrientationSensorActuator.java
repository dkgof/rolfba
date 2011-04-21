/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.androidgl.actuators;

import dk.lystrup.androidgl.SensorCore;
import dk.lystrup.androidgl.math.Quaternion;
import dk.lystrup.androidgl.nodes.Node;

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
