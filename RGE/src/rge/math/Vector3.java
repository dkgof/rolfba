
package rge.math;

import javax.vecmath.Vector3f;

/**
 * This class models a 3d vector
 * @author Rolf
 */
public class Vector3 {
    public static final Vector3 Zero = new Vector3(0,0,0);
    public static final Vector3 UnitX = new Vector3(1,0,0);
    public static final Vector3 UnitY = new Vector3(0,1,0);
    public static final Vector3 UnitZ = new Vector3(0,0,1);

    private double x, y, z;

    /**
     * Create a new Vector3 with the given parameters
     * @param x the x value of the new vector
     * @param y the y value of the new vector
     * @param z the z value of the new vector
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3f v) {
        this(v.x, v.y, v.z);
    }

    /**
     * Calculate the vector resulting from scaling this vector by scalar
     * @param scalar the scalar to scale this vector by
     * @return a new vector that is scaled
     */
    public Vector3 scale(double scalar) {
        return new Vector3(getX()*scalar, getY()*scalar, getZ()*scalar);
    }

    /**
     * Add vector v and this vector
     * @param v the vector to add to this vector
     * @return a new vector with the result from adding v to this vector
     */
    public Vector3 add(Vector3 v) {
        return new Vector3(v.getX()+getX(), v.getY()+getY(), v.getZ()+getZ());
    }

    /**
     * The length of this vector
     * @return the length of this vector
     */
    public double length() {
        return Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
    }

    /**
     * Subtract vector v from this vector
     * @param v the vector to subtract
     * @return a new vector with the result of subtracting v from this vector
     */
    public Vector3 subtract(Vector3 v) {
        return new Vector3(getX()-v.getX(), getY()-v.getY(), getZ()-v.getZ());
    }

    /**
     * Calculate the dot product of this vector and v
     * @param v the vector to calculate the dot product with
     * @return the dot product of this vector and v
     */
    public double dotProduct(Vector3 v) {
        return getX()*v.getX() + getY()*v.getY() + getZ()*v.getZ();
    }

    /**
     * Calculates the cross product of this vector and v
     * @param v the vector to calculate the cross product with
     * @return a new vector with the result of calculating the cross product
     * of this vector and v
     */
    public Vector3 crossProduct(Vector3 v) {
        double sx = getY()*v.getZ() - getZ()*v.getY();
        double sy = getZ()*v.getY() - getX()*v.getZ();
        double sz = getX()*v.getY() - getY()*v.getX();

        return UnitX.scale(sx).add(UnitY.scale(sy)).add(UnitZ.scale(sz));
    }

    /**
     * Calculate the angle to the given vector
     * @param v the vector to calculate the angle to
     * @return the angle to v
     */
    public double angleTo(Vector3 v) {
        double lengthABCross = this.crossProduct(v).length();
        double lengthAB = this.length() * v.length();

        return Math.asin(lengthABCross / lengthAB);
    }

    @Override
    public Vector3 clone() {
        return new Vector3(getX(), getY(), getZ());
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Vector3) {
            Vector3 v = (Vector3) o;

            return v.getX() == this.getX() && v.getY() == this.getY() && v.getZ() == this.getZ();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.getX()) ^ (Double.doubleToLongBits(this.getX()) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.getY()) ^ (Double.doubleToLongBits(this.getY()) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.getZ()) ^ (Double.doubleToLongBits(this.getZ()) >>> 32));
        return hash;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public double getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(double z) {
        this.z = z;
    }
}
