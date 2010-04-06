
package rge.actuators;

import rge.math.Quaternion;
import rge.math.Vector3;
import rge.nodes.Node;

/**
 *
 * @author Rolf
 */
public class ConstantRotationActuator implements Actuator {
    private final double dx;
    private final double dy;
    private final double dz;

    public ConstantRotationActuator(double dx, double dy, double dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;

    }

    @Override
    public void update(Node node, double deltaTime) {
        Quaternion qx = Quaternion.createFromAxisAngle(dx*deltaTime, Vector3.UnitX);
        Quaternion qy = Quaternion.createFromAxisAngle(dy*deltaTime, Vector3.UnitY);
        Quaternion qz = Quaternion.createFromAxisAngle(dz*deltaTime, Vector3.UnitZ);

        node.setRotation(node.getRotation().mult(qx.mult(qy).mult(qz)));
    }
}
