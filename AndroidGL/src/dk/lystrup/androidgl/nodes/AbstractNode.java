/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.nodes;

import dk.lystrup.androidgl.actuators.Actuator;
import dk.lystrup.androidgl.math.AxisAngle;
import dk.lystrup.androidgl.math.Quaternion;
import dk.lystrup.androidgl.math.Vector3;
import dk.lystrup.androidgl.textures.Texture;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public abstract class AbstractNode implements Node {
    protected Vector3 position;
    protected Quaternion rotation;
    protected Actuator actuator;

    private List<Node> children;

    private int[] TEXTURE_UNITS = {GL10.GL_TEXTURE0, GL10.GL_TEXTURE1, GL10.GL_TEXTURE2, GL10.GL_TEXTURE3};
    private List<Texture> textures;

    public AbstractNode() {
        children = new ArrayList<Node>();
        position = new Vector3();
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
        gl.glPushMatrix();
            AxisAngle axisRotation = rotation.toAxisAngle();
            gl.glTranslatef(position.getX(), position.getY(), position.getZ());
            gl.glRotatef(axisRotation.getAngle(), axisRotation.getAxis().getX(), axisRotation.getAxis().getY(), axisRotation.getAxis().getZ());

            for(Node child : children) {
                child.recursiveRender(gl);
            }

            activateTextures(gl);
            this.render(gl);
            deactivateTextures(gl);
        gl.glPopMatrix();
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

    private void activateTextures(GL10 gl) {
        gl.glEnable(GL10.GL_TEXTURE_2D);

        int i = 0;
        for(Texture tex : textures) {
            gl.glActiveTexture(TEXTURE_UNITS[i]);
            gl.glClientActiveTexture(TEXTURE_UNITS[i]);

            tex.bind(gl);

            i++;
        }
    }

    private void deactivateTextures(GL10 gl) {
        gl.glDisable(GL10.GL_TEXTURE_2D);
        int i = 0;
        for(Texture tex : textures) {
            gl.glActiveTexture(TEXTURE_UNITS[i]);
            gl.glClientActiveTexture(TEXTURE_UNITS[i]);

            tex.unbind(gl);

            i++;
        }
        gl.glActiveTexture(TEXTURE_UNITS[0]);
        gl.glClientActiveTexture(TEXTURE_UNITS[0]);
    }

    public void addTexture(Texture tex) {
        textures.add(tex);
    }

    public void setTexture(Texture tex) {
        textures.clear();
        textures.add(tex);
    }

    public void setTexture(Texture tex, int textureUnit) {
        textures.set(textureUnit, tex);
    }
}
