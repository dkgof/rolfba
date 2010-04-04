
package rge.math;

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
