
package rge.nodes;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author Rolf
 */
public class CubeNode extends Node {

    @Override
    public void render() {
        GL11.glBegin(GL11.GL_QUADS);
            //Front
            GL11.glVertex3d(-0.5, 0.5, 0.5);
            GL11.glVertex3d(-0.5, -0.5, 0.5);
            GL11.glVertex3d(0.5, -0.5, 0.5);
            GL11.glVertex3d(0.5, 0.5, 0.5);

            //Back
            GL11.glVertex3d(-0.5, 0.5, -0.5);
            GL11.glVertex3d(0.5, 0.5, -0.5);
            GL11.glVertex3d(0.5, -0.5, -0.5);
            GL11.glVertex3d(-0.5, -0.5, -0.5);

            //Top
            GL11.glVertex3d(-0.5, 0.5, -0.5);
            GL11.glVertex3d(-0.5, 0.5, 0.5);
            GL11.glVertex3d(0.5, 0.5, 0.5);
            GL11.glVertex3d(0.5, 0.5, -0.5);

            //Bottom
            GL11.glVertex3d(-0.5, -0.5, -0.5);
            GL11.glVertex3d(0.5, -0.5, -0.5);
            GL11.glVertex3d(0.5, -0.5, 0.5);
            GL11.glVertex3d(-0.5, -0.5, 0.5);

            //Left
            GL11.glVertex3d(0.5, 0.5, 0.5);
            GL11.glVertex3d(0.5, 0.5, -0.5);
            GL11.glVertex3d(0.5, -0.5, -0.5);
            GL11.glVertex3d(0.5, -0.5, 0.5);

            //Right
            GL11.glVertex3d(-0.5, 0.5, -0.5);
            GL11.glVertex3d(-0.5, -0.5, -0.5);
            GL11.glVertex3d(-0.5, -0.5, 0.5);
            GL11.glVertex3d(-0.5, 0.5, 0.5);

        GL11.glEnd();
    }

    @Override
    public void createPhysics(float mass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
