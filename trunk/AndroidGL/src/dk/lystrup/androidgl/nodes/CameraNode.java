
package dk.lystrup.androidgl.nodes;

import android.opengl.GLU;
import dk.lystrup.androidgl.Display;
import dk.lystrup.androidgl.math.Vector3;
import javax.microedition.khronos.opengles.GL10;

/**
 * This class models a Camera in OpenGL that when rendered will set up OpenGL's
 * projection matrix to correspond with the settings of the Camera.
 *
 * The CameraNode is always looking down its local Z-axis in negative direction
 *
 * @author Rolf
 */
public class CameraNode extends AbstractNode {

    private float fieldOfView;
    private float aspect;
    private float nearPlane;
    private float farPlane;

    private boolean recalculateAspect;

    public CameraNode() {
        this(60,1,0.1f,100);
    }

    public CameraNode(float fov, float aspect, float near, float far) {
        this.fieldOfView = fov;
        this.aspect = aspect;
        this.nearPlane = near;
        this.farPlane = far;

        recalculateAspect = true;
    }

    @Override
    public void render(GL10 gl) {
        Vector3 direction = position.add(Vector3.UnitZ.scale(-1));

        if(recalculateAspect) {
            aspect = Display.singleton().getWidth() / (Display.singleton().getHeight() * 1.0f);
        }

        gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            GLU.gluPerspective(gl, fieldOfView, aspect, nearPlane, farPlane);
            GLU.gluLookAt(gl, position.getX(), position.getY(), position.getZ(), direction.getX(), direction.getY(), direction.getZ(), 0, 1, 0);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    /**
     * @return the fov
     */
    public float getFieldOfView() {
        return fieldOfView;
    }

    /**
     * @param fov the fov to set
     */
    public void setFieldOfView(float fov) {
        this.fieldOfView = fov;
    }

    /**
     * @return the aspect
     */
    public float getAspect() {
        return aspect;
    }

    /**
     * @param aspect the aspect to set
     */
    public void setAspect(float aspect) {
        this.aspect = aspect;
    }

    /**
     * @return the nearPlane
     */
    public float getNearPlane() {
        return nearPlane;
    }

    /**
     * @param nearPlane the nearPlane to set
     */
    public void setNearPlane(float nearPlane) {
        this.nearPlane = nearPlane;
    }

    /**
     * @return the farPlane
     */
    public float getFarPlane() {
        return farPlane;
    }

    /**
     * @param farPlane the farPlane to set
     */
    public void setFarPlane(float farPlane) {
        this.farPlane = farPlane;
    }

    /**
     * Set if this CameraNode should recalculate its aspect according to the
     * width/height from Display.
     * @param b
     */
    public void setRecalculateAspect(boolean b) {
        recalculateAspect = b;
    }
}
