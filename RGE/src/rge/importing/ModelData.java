
package rge.importing;

import java.util.ArrayList;
import java.util.List;
import rge.math.Vector3;

/**
 *
 * @author Rolf
 */
public class ModelData {
    private List<Vector3> vertices;
    private List<Vector3> normals;
    private List<Point2> textureCoords;
    private List<Face> faces;

    public List<Face> getFaces() {
        return faces;
    }

    public List<Vector3> getNormals() {
        return normals;
    }

    public List<Point2> getTextureCoords() {
        return textureCoords;
    }

    public List<Vector3> getVertices() {
        return vertices;
    }

    public ModelData() {
        vertices = new ArrayList<Vector3>();
        normals = new ArrayList<Vector3>();
        faces = new ArrayList<Face>();
    }

    public void addVertex(Vector3 v) {
        vertices.add(v);
    }

    public void addNormal(Vector3 n) {
        normals.add(n);
    }

    public void addTextureCoord(Point2 t) {
        textureCoords.add(t);
    }

    public void addFace(Face f) {
        faces.add(f);
    }

    public Vector3 getVertex(int vertexIndex) {
        return vertices.get(vertexIndex-1);
    }

    public Vector3 getNormal(int normalIndex) {
        return normals.get(normalIndex-1);
    }
}
