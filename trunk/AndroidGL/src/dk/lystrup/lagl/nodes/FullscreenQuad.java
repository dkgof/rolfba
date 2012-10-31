/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.nodes;

/**
 * A fullscreen quad that will fill the entire screen if used in conjunction with a fullscreen vertex shader
 * @author Rolf Bagge
 */
public class FullscreenQuad extends AbstractNode {

    private static final float vertices[] = {
        -1f, -1f, 0.0f,
        1f, -1f, 0.0f,
        1f, 1f, 0.0f,
        -1f, 1f, 0.0f,};

    private static final short[] indices = {0, 1, 2, 0, 2, 3};
    
    private static final ModelData data = new ModelData(indices, vertices, null);
    private final ModelNode model;
    
    /**
     * Create a new TextureQuad
     */
    public FullscreenQuad() {
        model = new ModelNode(data);
    }
    
    @Override
    public void render() {
        model.render();
    }
}
