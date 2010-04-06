
package rge.math;

import javax.vecmath.Quat4f;

/**
 *
 * @author Rolf
 */
public class Quaternion {

    private double w;
    private Vector3 v;

    public Quaternion(double w, Vector3 v) {
        this.w = w;
        this.v = v.clone();
    }

    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.v = new Vector3(x,y,z);
    }

    public Quaternion(Quat4f q) {
        this(q.w, q.x, q.y, q.z);
    }

    public Quaternion add(Quaternion q) {
        return new Quaternion(w+q.w, v.add(q.v));
    }

    public Quaternion scale(double scalar) {
        return new Quaternion(w*scalar, v.scale(scalar));
    }

    public Quaternion normalize() {
        double n = Math.sqrt(v.getX()*v.getX() + v.getY()*v.getY() + v.getZ()*v.getZ() + w*w);

        return this.scale(1/n);
    }

    public Quaternion mult(Quaternion q) {
        double nx = v.getX()*q.w + v.getY()*q.getV().getZ() - v.getZ()*q.getV().getY() + w*q.getV().getX();
        double ny = -v.getX()*q.getV().getZ() + v.getY()*q.getW() + v.getZ()*q.getV().getX() + w*q.getV().getY();
        double nz = v.getX()*q.getV().getY() - v.getY()*q.getV().getX() + v.getZ()*q.getW() + w*q.getV().getZ();
        double nw = -v.getX()*q.getV().getX() - v.getY()*q.getV().getY() - v.getZ()*q.getV().getZ() + w*q.getW();

        return new Quaternion(nw, new Vector3(nx, ny, nz));
    }

    public Quaternion conjugate() {
        return new Quaternion(w, v.scale(-1));
    }

    public static Quaternion createFromAxisAngle(double angle, Vector3 axis) {
        angle = Math.toRadians(angle);
        double sinHalfAngle = Math.sin(angle / 2);
        return new Quaternion(Math.cos(angle/2), axis.scale(sinHalfAngle));
    }

    public AxisAngle toAxisAngle() {
        double scale = Math.sqrt(1 - w*w);
        return new AxisAngle(Math.toDegrees(2*Math.acos(w)), v.scale(1/scale));
    }

    /**
     * @return the q
     */
    public double getW() {
        return w;
    }

    /**
     * @return the v
     */
    public Vector3 getV() {
        return v;
    }

}
