
package dk.lystrup.androidgl.math;

/**
 *
 * @author Rolf
 */
public class AxisAngle {
    private final float angle;
    private final Vector3 axis;

    public AxisAngle(float angle, Vector3 axis) {
        this.angle = angle;
        this.axis = axis;
    }

    public AxisAngle(float angle, float x, float y, float z) {
        this(angle, new Vector3(x,y,z));
    }

    /**
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @return the axis
     */
    public Vector3 getAxis() {
        return axis;
    }

}
