
package rge.nodes;

import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;
import com.bulletphysics.extras.gimpact.GImpactMeshShape;
import com.bulletphysics.linearmath.Transform;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import rge.importing.Face;
import rge.importing.FacePoint;
import rge.importing.ModelData;
import rge.importing.Point2;
import rge.math.Vector3;
import rge.physics.PhysicsCore;

/**
 *
 * @author Rolf
 */
public class ModelNode extends Node {
    private ModelData data;
    private boolean useDisplayList;
    private int displayList;

    public ModelNode(ModelData data) {
        this(data, true);
    }

    public ModelNode(ModelData data, boolean useDisplayList) {
        this.data = data;
        this.useDisplayList = useDisplayList;
        displayList = -1;
    }

    @Override
    public void render() {
        if(useDisplayList) {
            if(displayList == -1) {
                displayList = GL11.glGenLists(1);

                GL11.glNewList(displayList, GL11.GL_COMPILE);
                    renderModel();
                GL11.glEndList();
            }

            GL11.glCallList(displayList);
        }
        else {
            renderModel();
        }
    }

    @Override
    public void update(double deltaTime) {
    }

    private void renderModel() {
        boolean inTriangleMode = true;
        
        GL11.glBegin(GL11.GL_TRIANGLES);
            for(Face f : data.getFaces()) {
                if(f.isTriangle()) {
                    if(!inTriangleMode) {
                        GL11.glEnd();
                        GL11.glBegin(GL11.GL_TRIANGLES);
                    }
                }
                else if( f.isQuad()) {
                    if(inTriangleMode) {
                        GL11.glEnd();
                        GL11.glBegin(GL11.GL_QUADS);
                    }
                }
                else {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Face is not a triangle or quad!");
                }
                for(FacePoint fp : f.getPoints()) {
                    Vector3 vertex = data.getVertex(fp.getVertex());
                    Vector3 normal = data.getNormal(fp.getNormal());

                    int textureUnitIndex = 0;
                    for(int tpIndex : fp.getTextureCoords()) {
                        Point2 tp = data.getTextureCoords(tpIndex);
                        GL13.glMultiTexCoord2d(Node.TEXTURE_UNIT[textureUnitIndex], tp.getS(), tp.getT());
                        textureUnitIndex++;
                    }

                    GL11.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
                    GL11.glNormal3d(normal.getX(), normal.getY(), normal.getZ());
                }
            }
        GL11.glEnd();
    }

    @Override
    public void createPhysics(float mass) {
        TriangleIndexVertexArray indexVertexArray = new TriangleIndexVertexArray(
                data.getFaces().size(), data.getTriangleData(), 3 * 4,
                data.getVertices().size(), data.getVertexData(), 3 * 4);

        GImpactMeshShape trimesh = new GImpactMeshShape(indexVertexArray);
        trimesh.setLocalScaling(new Vector3f((float) getScale().getX(), (float) getScale().getY(), (float) getScale().getZ()));
        trimesh.updateBound();

        Transform startTransform = new Transform();
        startTransform.setFromOpenGLMatrix(getTransformMatrix());

        setPhysicsBody(PhysicsCore.singleton().localCreateRigidBody(mass, startTransform, trimesh));
    }

}
