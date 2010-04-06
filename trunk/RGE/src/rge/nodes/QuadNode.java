
package rge.nodes;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author Rolf
 */
public class QuadNode extends Node {

    @Override
    public void render() {
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0, 1);
            GL11.glVertex2d(-0.5, 0.5);
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2d(-0.5, -0.5);
            GL11.glTexCoord2d(1, 0);
            GL11.glVertex2d(0.5, -0.5);
            GL11.glTexCoord2d(1, 1);
            GL11.glVertex2d(0.5, 0.5);
        GL11.glEnd();
    }

    @Override
    public void createPhysics(float mass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
