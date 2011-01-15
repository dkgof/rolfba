
package dk.lystrup.androidgl.math;

/**
 * This class models a 3d vector
 * @author Rolf
 */
public class Vector3 {
    public static final Vector3 Zero = new Vector3(0,0,0);
    public static final Vector3 UnitX = new Vector3(1,0,0);
    public static final Vector3 UnitY = new Vector3(0,1,0);
    public static final Vector3 UnitZ = new Vector3(0,0,1);

    private final float x, y, z;

    public Vector3() {
        this(0,0,0);
    }

    /**
     * Create a new Vector3 with the given parameters
     * @param x the x value of the new vector
     * @param y the y value of the new vector
     * @param z the z value of the new vector
     */
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculate the vector resulting from scaling this vector by scalar
     * @param scalar the scalar to scale this vector by
     * @return a new vector that is scaled
     */
    public Vector3 scale(float scalar) {
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
    public float length() {
        return (float) Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
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
    public float dotProduct(Vector3 v) {
        return getX()*v.getX() + getY()*v.getY() + getZ()*v.getZ();
    }

    /**
     * Calculates the cross product of this vector and v
     * @param v the vector to calculate the cross product with
     * @return a new vector with the result of calculating the cross product
     * of this vector and v
     */
    public Vector3 crossProduct(Vector3 v) {
        float sx = getY()*v.getZ() - getZ()*v.getY();
        float sy = getZ()*v.getY() - getX()*v.getZ();
        float sz = getX()*v.getY() - getY()*v.getX();

        return UnitX.scale(sx).add(UnitY.scale(sy)).add(UnitZ.scale(sz));
    }

    /**
     * Calculate the angle to the given vector
     * @param v the vector to calculate the angle to
     * @return the angle to v
     */
    public float angleTo(Vector3 v) {
        float lengthABCross = this.crossProduct(v).length();
        float lengthAB = this.length() * v.length();

        return (float) Math.asin(lengthABCross / lengthAB);
    }

    @Override
    public Vector3 clone() {
        return new Vector3(x, y, z);
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
        hash = 59 * hash + Float.floatToIntBits(this.x);
        hash = 59 * hash + Float.floatToIntBits(this.y);
        hash = 59 * hash + Float.floatToIntBits(this.z);
        return hash;
    }

    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @return the z
     */
    public float getZ() {
        return z;
    }

    public Vector3 normalize() {
        float lengthScale = 1.0f / this.length();
        return this.scale(lengthScale);
    }
}
