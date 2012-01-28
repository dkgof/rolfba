
package dk.lystrup.lagl.nodes;

import android.opengl.GLU;
import android.opengl.Matrix;
import dk.lystrup.lagl.Display;
import dk.lystrup.lagl.LAGLMatrix;
import dk.lystrup.lagl.math.Vector3;

/**
 * This class models a Camera in OpenGL that when rendered will set up OpenGL's
 * projection matrix to correspond with the settings of the Camera.
 *
 * The CameraNode is always looking down its local Z-axis in negative direction
 * 
 * The camera can be set to automatically recalculate its aspect from the display
 * width and height.
 *
 * @author Rolf
 */
public class CameraNode extends AbstractNode {

    private float fieldOfView;
    private float aspect;
    private float nearPlane;
    private float farPlane;

    private boolean recalculateAspect;

    /**
     * Create a new camera with default settings
     * Field of view: 60
     * Aspect: 1.0
     * Near plane: 0.1
     * Far plane: 100
     */
    public CameraNode() {
        this(60,1,0.1f,100);
    }

    /**
     * Create a new camera with the given parameters
     * @param fov the field of view of the camera
     * @param aspect the aspect of the camera
     * @param near the near clip plane of the camera
     * @param far the far clip plane of the camera
     */
    public CameraNode(float fov, float aspect, float near, float far) {
        this.fieldOfView = fov;
        this.aspect = aspect;
        this.nearPlane = near;
        this.farPlane = far;

        recalculateAspect = true;
    }

    @Override
    public void render() {
        Vector3 direction = position.add(Vector3.UnitZ.scale(-1));

        if(recalculateAspect) {
            aspect = Display.singleton().getWidth() / (Display.singleton().getHeight() * 1.0f);
        }

        Matrix.frustumM(LAGLMatrix.singleton().getMatrix(LAGLMatrix.MatrixType.PROJECTION), 0, -aspect, aspect, -1, 1, nearPlane, farPlane); 
        Matrix.setLookAtM(LAGLMatrix.singleton().getMatrix(LAGLMatrix.MatrixType.VIEW), 0, position.getX(), position.getY(), position.getZ(), direction.getX(), direction.getY(), direction.getZ(), 0, 1, 0);
    }

    /**
     * @return the Field of view
     */
    public float getFieldOfView() {
        return fieldOfView;
    }

    /**
     * @param fov the new field of view to set
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
