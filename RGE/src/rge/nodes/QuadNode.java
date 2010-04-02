
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
            GL11.glVertex2d(-0.5, 0.5);
            GL11.glVertex2d(-0.5, -0.5);
            GL11.glVertex2d(0.5, -0.5);
            GL11.glVertex2d(0.5, 0.5);
        GL11.glEnd();
    }

    @Override
    public void update(double deltaTime) {
    }

}
