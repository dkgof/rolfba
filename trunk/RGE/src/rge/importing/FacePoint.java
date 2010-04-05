
package rge.importing;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rolf
 */
public class FacePoint {
    private int vertex;
    private int normal;
    private List<Integer> textureCoords;

    public FacePoint(int vertex, int normal) {
        this.vertex = vertex;
        this.normal = normal;
        textureCoords = new ArrayList<Integer>();
    }

    /**
     * @return the vertex
     */
    public int getVertex() {
        return vertex;
    }

    /**
     * @return the normal
     */
    public int getNormal() {
        return normal;
    }

    /**
     * @return the textureCoords
     */
    public List<Integer> getTextureCoords() {
        return textureCoords;
    }

    public void addTextureCoord(int textureCoord) {
        textureCoords.add(textureCoord);
    }
}
