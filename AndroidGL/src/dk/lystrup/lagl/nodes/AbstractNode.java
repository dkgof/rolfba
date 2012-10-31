/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.nodes;

import android.opengl.GLES20;
import dk.lystrup.lagl.LAGLMatrix;
import dk.lystrup.lagl.LAGLUtil;
import dk.lystrup.lagl.actuators.Actuator;
import dk.lystrup.lagl.math.Quaternion;
import dk.lystrup.lagl.math.Vector3;
import dk.lystrup.lagl.shaders.Shader;
import dk.lystrup.lagl.shaders.glsl.DefaultShader;
import dk.lystrup.lagl.textures.Texture;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementing class with all the basic parts of the Node interface
 * @author Rolf
 */
public abstract class AbstractNode implements Node {
    public static final int[] TEXTURE_UNITS = {GLES20.GL_TEXTURE0, GLES20.GL_TEXTURE1, GLES20.GL_TEXTURE2, GLES20.GL_TEXTURE3};

    protected Vector3 position;
    protected Vector3 scale;
    protected Quaternion rotation;
    protected Actuator actuator;

    protected final List<Node> children;

    protected Shader shader;

    private final List<Texture> textures;
    
    public AbstractNode() {
        children = new ArrayList<Node>();
        position = new Vector3();
        scale = new Vector3(1,1,1);
        rotation = Quaternion.createFromAxisAngle(0, Vector3.UnitX);
        textures = new ArrayList<Texture>();
        shader = new DefaultShader();
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
    public void recursiveRender() {
        LAGLMatrix.singleton().pushMatrix(LAGLMatrix.MatrixType.MODEL);
            LAGLMatrix.singleton().translate(LAGLMatrix.MatrixType.MODEL, position);
            LAGLMatrix.singleton().rotate(LAGLMatrix.MatrixType.MODEL, rotation);
            LAGLMatrix.singleton().scale(LAGLMatrix.MatrixType.MODEL, scale);

            for(Node child : children) {
                child.recursiveRender();
            }

            activateTextures();
            if(shader != null) {
                shader.bind();
            }
            this.render();
            if(shader != null) {
                shader.unbind();
            }
            deactivateTextures();
        LAGLMatrix.singleton().popMatrix(LAGLMatrix.MatrixType.MODEL);
    }

    public abstract void render();

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

    protected void activateTextures() {
        int i = 0;
        for(Texture tex : textures) {
            GLES20.glActiveTexture(TEXTURE_UNITS[i]);
            LAGLUtil.checkGlError("glActiveTexture - "+TEXTURE_UNITS[i]);

            tex.bind();

            i++;
        }
    }

    protected void deactivateTextures() {
        int i = 0;
        for(Texture tex : textures) {
            GLES20.glActiveTexture(TEXTURE_UNITS[i]);
            LAGLUtil.checkGlError("glActiveTexture - "+TEXTURE_UNITS[i]);

            tex.unbind();

            i++;
        }
        GLES20.glActiveTexture(TEXTURE_UNITS[0]);
        LAGLUtil.checkGlError("glActiveTexture - "+TEXTURE_UNITS[0]);
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
    
    @Override
    public void setShader(Shader shader) {
        this.shader = shader;
    }
}
