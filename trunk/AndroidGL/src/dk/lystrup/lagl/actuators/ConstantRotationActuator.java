/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.actuators;

import android.util.Log;
import dk.lystrup.lagl.math.Quaternion;
import dk.lystrup.lagl.math.Vector3;
import dk.lystrup.lagl.nodes.Node;

/**
 * This Actuator rotates the Node's its attached too by a constant speed
 * @author Rolf
 */
public class ConstantRotationActuator implements Actuator {

    private float speedX;
    private float speedY;
    private float speedZ;

    public ConstantRotationActuator(float xSpeed, float ySpeed, float zSpeed) {
        this.speedX = xSpeed;
        this.speedY = ySpeed;
        this.speedZ = zSpeed;
    }

    public void update(Node node, float deltaTime) {
        Quaternion xRot = Quaternion.createFromAxisAngle(speedX * deltaTime, Vector3.UnitX);
        Quaternion yRot = Quaternion.createFromAxisAngle(speedY * deltaTime, Vector3.UnitY);
        Quaternion zRot = Quaternion.createFromAxisAngle(speedZ * deltaTime, Vector3.UnitZ);
        
        node.setRotation(xRot.mult(yRot).mult(zRot).mult(node.getRotation()));
        
        //Log.i("LAGL", "Rotation set: "+node.getRotation());
    }

}
