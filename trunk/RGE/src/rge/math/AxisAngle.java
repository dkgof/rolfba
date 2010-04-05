
package rge.math;

import javax.vecmath.Quat4f;

/**
 *
 * @author Rolf
 */
public class AxisAngle {
    private double angle;
    private Vector3 axis;

    public AxisAngle(double angle, Vector3 axis) {
        this.angle = angle;
        this.axis = axis;
    }

    public AxisAngle(double angle, double x, double y, double z) {
        this(angle, new Vector3(x,y,z));
    }

    public AxisAngle(Quat4f q) {
        this(Math.toDegrees(2*Math.acos(q.w)), new Vector3(q.x, q.z, q.y).scale(1/Math.sqrt(1 - q.w * q.w)));
    }
    
    /**
     * @return the angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * @return the axis
     */
    public Vector3 getAxis() {
        return axis;
    }

}
