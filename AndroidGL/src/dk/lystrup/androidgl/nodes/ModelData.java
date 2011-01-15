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
    private float[] vertices;
    //The indices of this model
    private short[] indices;
    //The texture coordinates of this model
    private float[] texturecoords;

    /**
     * @return the vertices
     */
    public float[] getVertices() {
        return vertices;
    }

    /**
     * @param vertices the vertices to set
     */
    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    /**
     * @return the indices
     */
    public short[] getIndices() {
        return indices;
    }

    /**
     * @param indices the indices to set
     */
    public void setIndices(short[] indices) {
        this.indices = indices;
    }

    /**
     * @return the texturecoords
     */
    public float[] getTexturecoords() {
        return texturecoords;
    }

    /**
     * @param texturecoords the texturecoords to set
     */
    public void setTexturecoords(float[] texturecoords) {
        this.texturecoords = texturecoords;
    }
}
