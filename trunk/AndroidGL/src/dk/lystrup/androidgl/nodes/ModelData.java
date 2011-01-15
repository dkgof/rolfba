/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.nodes;

/**
 * This class represents a model with vertices and indices used to describe the
 * faces of the represented 3d model.
 * @author Rolf
 */
public class ModelData {

    //The vertices of this model
    private final float[] vertices;
    //The indices of this model
    private final short[] indices;
    //The texture coordinates of this model
    private final float[] texturecoords;

    ModelData(short[] indices, float[] vertices, float[] texturecoords) {
        this.indices = indices;
        this.vertices = vertices;
        this.texturecoords = texturecoords;
    }

    /**
     * @return the vertices
     */
    public float[] getVertices() {
        return vertices;
    }

    /**
     * @return the indices
     */
    public short[] getIndices() {
        return indices;
    }

    /**
     * @return the texturecoords
     */
    public float[] getTexturecoords() {
        return texturecoords;
    }
}
