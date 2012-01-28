/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.nodes;

import static android.opengl.GLES10.*;

import dk.lystrup.androidgl.actuators.Actuator;
import dk.lystrup.androidgl.math.AxisAngle;
import dk.lystrup.androidgl.math.Quaternion;
import dk.lystrup.androidgl.math.Vector3;
import dk.lystrup.androidgl.textures.Texture;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

/**
 * Implementing class with all the basic parts of the Node interface
 * @author Rolf
 */
public abstract class AbstractNode implements Node {
    public static final int[] TEXTURE_UNITS = {GL_TEXTURE0, GL_TEXTURE1, GL_TEXTURE2, GL_TEXTURE3};

    protected Vector3 position;
    protected Vector3 scale;
    protected Quaternion rotation;
    protected Actuator actuator;

    protected final List<Node> children;

    private final List<Texture> textures;

    public AbstractNode() {
        children = new ArrayList<Node>();
        position = new Vector3();
        scale = new Vector3(1,1,1);
        rotation = Quaternion.createFromAxisAngle(0, Vector3.UnitX);
        textures = new ArrayList<Texture>();
    }

    @Override
    public Vector3 getPosition() {
        return position;
    }

    @Override
    public Quaternion getRotation() {
        return rotation;
    }

    @Override
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        this.setPosition(new Vector3(x,y,z));
    }

    @Override
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    @Override
    public void recursiveRender(GL10 gl) {
        glPushMatrix();
            AxisAngle axisRotation = rotation.toAxisAngle();
            glTranslatef(position.getX(), position.getY(), position.getZ());
            glRotatef(axisRotation.getAngle(), axisRotation.getAxis().getX(), axisRotation.getAxis().getY(), axisRotation.getAxis().getZ());
            glScalef(scale.getX(), scale.getY(), scale.getZ());

            for(Node child : children) {
                child.recursiveRender(gl);
            }

            activateTextures(gl);
            this.render(gl);
            deactivateTextures(gl);
        glPopMatrix();
    }

    public abstract void render(GL10 gl);

    @Override
    public void attachNode(Node n) {
        children.add(n);
    }

    @Override
    public void setActuator(Actuator act) {
        actuator = act;
    }

    @Override
    public void recursiveUpdate(float deltaTime) {
        for(Node child : children) {
            child.recursiveUpdate(deltaTime);
        }

        if(actuator != null) {
            actuator.update(this, deltaTime);
        }
    }

    protected void activateTextures(GL10 gl) {
        glEnable(GL_TEXTURE_2D);

        int i = 0;
        for(Texture tex : textures) {
            glActiveTexture(TEXTURE_UNITS[i]);
            glClientActiveTexture(TEXTURE_UNITS[i]);

            tex.bind(gl);

            i++;
        }
    }

    protected void deactivateTextures(GL10 gl) {
        glDisable(GL_TEXTURE_2D);
        int i = 0;
        for(Texture tex : textures) {
            glActiveTexture(TEXTURE_UNITS[i]);
            glClientActiveTexture(TEXTURE_UNITS[i]);

            tex.unbind(gl);

            i++;
        }
        glActiveTexture(TEXTURE_UNITS[0]);
        glClientActiveTexture(TEXTURE_UNITS[0]);
    }

    @Override
    public void addTexture(Texture tex) {
        textures.add(tex);
    }

    @Override
    public void setTexture(Texture tex) {
        textures.clear();
        textures.add(tex);
    }

    @Override
    public void setTexture(Texture tex, int textureUnit) {
        textures.set(textureUnit, tex);
    }

    @Override
    public void setScale(float scale) {
        this.scale = new Vector3(scale, scale, scale);
    }

    @Override
    public void setScale(float x, float y, float z) {
        this.scale = new Vector3(x, y, z);
    }
}
