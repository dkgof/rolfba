/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.actuators;

import dk.lystrup.androidgl.math.AxisAngle;
import dk.lystrup.androidgl.math.Quaternion;
import dk.lystrup.androidgl.nodes.Node;

/**
 * This Actuator rotates the Node's its attached too by a constant speed
 * @author Rolf
 */
public class ConstantRotationActuator implements Actuator {

    private AxisAngle rotation;

    /**
     * Create a new ConstantRotationActuator that rotates with the given
     * Quaternion each second
     * @param rot the Quaternion to rotate / second
     */
    public ConstantRotationActuator(AxisAngle rot) {
        rotation = rot;
    }

    public void update(Node node, float deltaTime) {
        Quaternion newRotation = Quaternion.createFromAxisAngle(rotation.getAngle() * deltaTime, rotation.getAxis());
        node.setRotation(node.getRotation().mult(newRotation));
    }

}
