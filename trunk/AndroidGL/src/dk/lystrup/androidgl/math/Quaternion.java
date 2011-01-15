package dk.lystrup.androidgl.math;

/**
 *
 * @author Rolf
 */
public class Quaternion {

    private final float w;
    private final Vector3 v;

    public Quaternion() {
        this(0, 0, 0, 0);
    }

    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.v = new Vector3(x, y, z);
    }

    public Quaternion(float w, Vector3 v) {
        this.w = w;
        this.v = v.clone();
    }

    public Quaternion add(Quaternion q) {
        return new Quaternion(w + q.w, v.add(q.v));
    }

    public Quaternion scale(float scalar) {
        return new Quaternion(w * scalar, v.scale(scalar));
    }

    public Quaternion normalize() {
        float n = (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ() + w * w);

        return this.scale(1 / n);
    }

    public Quaternion mult(Quaternion q) {
        float nx = v.getX() * q.w + v.getY() * q.getV().getZ() - v.getZ() * q.getV().getY() + w * q.getV().getX();
        float ny = -v.getX() * q.getV().getZ() + v.getY() * q.getW() + v.getZ() * q.getV().getX() + w * q.getV().getY();
        float nz = v.getX() * q.getV().getY() - v.getY() * q.getV().getX() + v.getZ() * q.getW() + w * q.getV().getZ();
        float nw = -v.getX() * q.getV().getX() - v.getY() * q.getV().getY() - v.getZ() * q.getV().getZ() + w * q.getW();

        return new Quaternion(nw, new Vector3(nx, ny, nz));
    }

    public Quaternion conjugate() {
        return new Quaternion(w, v.scale(-1));
    }

    public static Quaternion createFromAxisAngle(float angle, Vector3 axis) {
        angle = (float) Math.toRadians(angle);
        float sinHalfAngle = (float) Math.sin(angle / 2);
        return new Quaternion((float) Math.cos(angle / 2), axis.scale(sinHalfAngle));
    }

    public AxisAngle toAxisAngle() {
        float scale = v.length();

        Vector3 axis;
        if (scale == 0) {
            axis = Vector3.UnitX;
        } else {
            axis = v.scale(1 / scale);
            axis = axis.normalize();
        }
        float angle = (float) Math.toDegrees(2.0f * Math.acos(w));
        return new AxisAngle(angle, axis);
    }

    @Override
    public Quaternion clone() {
        return new Quaternion(w, v.clone());
    }

    /**
     * @return the q
     */
    public float getW() {
        return w;
    }

    /**
     * @return the v
     */
    public Vector3 getV() {
        return v;
    }
}
