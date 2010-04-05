
package rge.nodes;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import rge.math.Vector3;

/**
 *
 * @author Rolf
 */
public class CameraNode extends Node {

    private double fov;
    private double aspect;
    private double nearPlane;
    private double farPlane;

    public CameraNode() {
        fov = 60;
        aspect = 1;
        nearPlane = 0.1;
        farPlane = 100;
    }

    @Override
    public void render() {
        Vector3 direction = this.getPosition().add(Vector3.UnitZ);
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GLU.gluPerspective((float)getFov(), (float)getAspect(), (float)getNearPlane(), (float)getFarPlane());
            GLU.gluLookAt((float)this.getPosition().getX(), (float)this.getPosition().getY(), (float)this.getPosition().getZ(), (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), 0, 1, 0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @Override
    public void update(double deltaTime) {
    }

    /**
     * @return the fov
     */
    public double getFov() {
        return fov;
    }

    /**
     * @param fov the fov to set
     */
    public void setFov(double fov) {
        this.fov = fov;
    }

    /**
     * @return the aspect
     */
    public double getAspect() {
        return aspect;
    }

    /**
     * @param aspect the aspect to set
     */
    public void setAspect(double aspect) {
        this.aspect = aspect;
    }

    /**
     * @return the nearPlane
     */
    public double getNearPlane() {
        return nearPlane;
    }

    /**
     * @param nearPlane the nearPlane to set
     */
    public void setNearPlane(double nearPlane) {
        this.nearPlane = nearPlane;
    }

    /**
     * @return the farPlane
     */
    public double getFarPlane() {
        return farPlane;
    }

    /**
     * @param farPlane the farPlane to set
     */
    public void setFarPlane(double farPlane) {
        this.farPlane = farPlane;
    }

    @Override
    public void createPhysics(float mass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
