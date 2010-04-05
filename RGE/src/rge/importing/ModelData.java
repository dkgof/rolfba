
package rge.importing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        textureCoords = new ArrayList<Point2>();
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

    public Point2 getTextureCoords(int tpIndex) {
        return textureCoords.get(tpIndex-1);
    }

    private boolean countDone = false;

    public int getVertexCount() {
        if(!countDone) {
            doCount();
        }

        return vertexCount;
    }

    public int getNormalCount() {
        if(!countDone) {
            doCount();
        }

        return normalCount;
    }

    public String getStats() {
        return "* Vertices: "+getVertexCount()+"\n" +
        "* Normals:  "+getNormalCount()+"\n" +
        "* Faces:    "+faces.size()+"\n";
    }

    private int vertexCount;
    private int normalCount;

    private synchronized void doCount() {
        Set<Integer> vertexSet = new HashSet<Integer>();
        Set<Integer> normalSet = new HashSet<Integer>();

        for(Face f : faces) {
            for(FacePoint fp : f.getPoints()) {
                vertexSet.add(fp.getVertex());
                normalSet.add(fp.getNormal());
            }
        }

        vertexCount = vertexSet.size();
        normalCount = normalSet.size();

        countDone = true;
    }

    private ByteBuffer triangleData, vertexData;
    private boolean triangleVertexDataAvaliable = false;

    public ByteBuffer getTriangleData() {
        if(!triangleVertexDataAvaliable) {
            makeTriangleVertexData();
        }

        return triangleData;
    }

    public ByteBuffer getVertexData() {
        if(!triangleVertexDataAvaliable) {
            makeTriangleVertexData();
        }

        return vertexData;
    }

    private synchronized void makeTriangleVertexData() {
        triangleData = ByteBuffer.allocateDirect(faces.size() * 3 * 4).order(ByteOrder.nativeOrder());
        vertexData = ByteBuffer.allocateDirect(vertices.size() * 3 * 4).order(ByteOrder.nativeOrder());

        for(Face f : faces) {
            if(!f.isTriangle()) {
                throw new IllegalStateException("Only triangle mesh supported for physics creation!");
            }
            for(FacePoint fp : f.getPoints()) {
                triangleData.putInt(fp.getVertex()-1);
            }
        }

        for(Vector3 v : vertices) {
            vertexData.putFloat((float)v.getX());
            vertexData.putFloat((float)v.getY());
            vertexData.putFloat((float)v.getZ());
        }

        triangleData.flip();
        vertexData.flip();

        triangleVertexDataAvaliable = true;
    }
}
