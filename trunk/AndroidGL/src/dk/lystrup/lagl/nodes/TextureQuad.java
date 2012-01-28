/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.nodes;

/**
 * A TextureQuad is a 3d plane with an associated texture, great for use as
 * for example a billboard or any other flat surface that is textured.
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
    
    /**
     * Create a new TextureQuad
     */
    public TextureQuad() {
        model = new ModelNode(data);
    }
    
    @Override
    public void render() {
        model.render();
    }
}
