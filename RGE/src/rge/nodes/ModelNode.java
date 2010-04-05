
package rge.nodes;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import rge.importing.Face;
import rge.importing.FacePoint;
import rge.importing.ModelData;
import rge.math.Vector3;

/**
 *
 * @author Rolf
 */
public class ModelNode extends Node {
    private final ModelData data;
    private final boolean useDisplayList;
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
        GL11.glBegin(GL11.GL_TRIANGLES);
            for(Face f : data.getFaces()) {
                if(!f.isTriangle()) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Face is not a triangle!");
                }
                
                for(FacePoint fp : f.getPoints()) {
                    Vector3 vertex = data.getVertex(fp.getVertex());
                    Vector3 normal = data.getNormal(fp.getNormal());

                    GL11.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
                    GL11.glNormal3d(normal.getX(), normal.getY(), normal.getZ());
                }
            }
        GL11.glEnd();
    }

}
