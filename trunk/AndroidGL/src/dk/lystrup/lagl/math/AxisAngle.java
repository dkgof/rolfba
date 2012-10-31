
package dk.lystrup.lagl.math;

/**
 * AxisAngle represents an angle of rotation around a fixed axis.
 * @author Rolf
 */
public class AxisAngle {
    private final float angle;
    private final Vector3 axis;

    /**
     * Create a new AxisAngle rotating angle degrees around the given axis
     * @param angle the number of degrees to rotate
     * @param axis the axis to rotate around
     */
    public AxisAngle(float angle, Vector3 axis) {
        this.angle = angle;
        this.axis = axis;
    }

    /**
     * Create a new AxisAngle rotating angle degrees around the given axis
     * @param angle the number of degrees to rotate
     * @param x the x part of the axis to rotate around
     * @param y the x part of the axis to rotate around
     * @param z the x part of the axis to rotate around
     */
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

    @Override
    public String toString() {
        return "Angle: "+angle+" - Axis: "+axis;
    }
}
