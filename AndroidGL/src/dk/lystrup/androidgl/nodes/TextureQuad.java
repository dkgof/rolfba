/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.nodes;

import dk.lystrup.androidgl.math.AxisAngle;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public class TextureQuad extends AbstractNode {

    private static final float vertices[] = {
        -0.5f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.5f, 0.5f, 0.0f,};

    private static final float textures[] = {
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, 0.0f};

    private static final short[] indices = {0, 1, 2, 0, 2, 3};
    
    private static final ModelData data = new ModelData(indices, vertices, textures);
    private final ModelNode model;
    
    public TextureQuad() {
        model = new ModelNode(data);
    }
    
    @Override
    public void render(GL10 gl) {
        model.render(gl);
    }

    @Override
    public void recursiveRender(GL10 gl) {
        gl.glPushMatrix();
            AxisAngle axisRotation = rotation.toAxisAngle();
            gl.glTranslatef(position.getX(), position.getY(), position.getZ());
            gl.glRotatef(axisRotation.getAngle(), axisRotation.getAxis().getX(), axisRotation.getAxis().getY(), axisRotation.getAxis().getZ());

            for(Node child : children) {
                child.recursiveRender(gl);
            }

            activateTextures(gl);
            model.render(gl);
            deactivateTextures(gl);
        gl.glPopMatrix();
    }
}
