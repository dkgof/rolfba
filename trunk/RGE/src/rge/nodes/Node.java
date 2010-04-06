package rge.nodes;

import com.bulletphysics.dynamics.RigidBody;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import rge.actuators.Actuator;
import rge.math.AxisAngle;
import rge.math.Quaternion;
import rge.math.Vector3;
import rge.texture.Texture;

/**
 * A node is the basic component of the scene graph
 * @author Rolf
 */
public abstract class Node {

    public static int[] TEXTURE_UNIT = {GL13.GL_TEXTURE0, GL13.GL_TEXTURE1, GL13.GL_TEXTURE2, GL13.GL_TEXTURE3, GL13.GL_TEXTURE4, GL13.GL_TEXTURE5, GL13.GL_TEXTURE6, GL13.GL_TEXTURE7, GL13.GL_TEXTURE8, GL13.GL_TEXTURE9};

    //The children of this node in the scene graph
    private List<Node> children;

    //The actuators attached to this node
    private Actuator actuator;

    //The current position of this node
    private Vector3 position;

    //The current scale of this node
    private Vector3 scale;

    //The current rotation of this node
    private Quaternion rotation;

    private List<Texture> textures;

    private RigidBody physicsBody;

    private Quat4f physOrient = new Quat4f();
    private Vector3f physLocation = new Vector3f();

    public Node() {
        children = new ArrayList<Node>();
        position = new Vector3(0,0,0);
        scale = new Vector3(1,1,1);
        rotation = Quaternion.createFromAxisAngle(0, Vector3.UnitX);
        textures = new ArrayList<Texture>();
    }

    /**
     * Renders all the children of this node, and then renders the node itself
     */
    public void recursiveRender() {
        GL11.glPushMatrix();

        if(physicsBody != null) {
            physLocation = physicsBody.getCenterOfMassPosition(physLocation);
            setPosition(physLocation.x, physLocation.y, physLocation.z);
        }
        GL11.glTranslated(getPosition().getX(), getPosition().getY(), getPosition().getZ());

        if(physicsBody != null) {
            physOrient = physicsBody.getOrientation(physOrient);
            setRotation(new Quaternion(physOrient.w, physOrient.x, physOrient.y, physOrient.z));
        }
        AxisAngle axisAngle = getRotation().toAxisAngle();
        GL11.glRotatef((float)axisAngle.getAngle(), (float)axisAngle.getAxis().getX(), (float)axisAngle.getAxis().getY(), (float)axisAngle.getAxis().getZ());

        GL11.glScaled(getScale().getX(), getScale().getY(), getScale().getZ());

        for(Node n : getChildren()) {
            n.recursiveRender();
        }

        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
        activateTextures();
        this.render();
        GL11.glPopAttrib();

        GL11.glPopMatrix();
    }

    /**
     * Renders this node
     */
    public abstract void render();

    /**
     * Runs update on all children
     * @param deltaTime the time since last update
     */
    public void recursiveUpdate(double deltaTime) {
        for(Node n : getChildren()) {
            n.recursiveUpdate(deltaTime);
        }

        this.update(deltaTime);
    }

    /**
     * Do updating of this node
     * @param deltaTime the time since last update
     */
    public void update(double deltaTime) {
        if(getActuator() != null) {
            getActuator().update(this, deltaTime);
        }
    }

    /**
     * Translate this nodes position to the given coordinates (x,y,z)
     * @param x the position on the x axis
     * @param y the position on the y axis
     * @param z the position on the z axis
     */
    public void setPosition(double x, double y, double z) {
        setPosition(new Vector3(x, y, z));
    }

    /**
     * Scales this node with the given values on each axis.
     * @param x the amount to scale on the x axis
     * @param y the amount to scale on the y axis
     * @param z the amount to scale on the z axis
     */
    public void setScale(double x, double y, double z) {
        setScale(new Vector3(x, y, z));
    }

    /**
     * Scales this node uniformly on all 3 axis with the given scale
     * @param scale the amount to scale
     */
    public void setScale(double scale) {
        this.setScale(new Vector3(scale, scale, scale));
    }

    /**
     * Attach the given node as a child of this node
     * @param n the node to attach
     */
    public void attachNode(Node n) {
        getChildren().add(n);
    }

    /**
     * @return the children
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * @return the position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * @return the scale
     */
    public Vector3 getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Vector3 scale) {
        this.scale = scale;
    }

    /**
     * @return the rotation
     */
    public Quaternion getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    private void activateTextures() {
        int textureUnitIndex = 0;
        for(Texture tex : getTextures()) {
            GL13.glActiveTexture(TEXTURE_UNIT[textureUnitIndex]);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            tex.bind();
            textureUnitIndex++;
        }
    }

    /**
     * Set the texture of this node to the given texture,
     * this clears all other textures from this node
     * @param tex the texture to set
     */
    public void setTexture(Texture tex) {
        getTextures().clear();
        getTextures().add(tex);
    }

    /**
     * Add another texture to this node
     * @param tex the texture to add
     */
    public void addTexture(Texture tex) {
        getTextures().add(tex);
    }

    /**
     * Create a static physics body of this node, with mass = 0
     */
    public void createPhysics() {
        createPhysics(0f);
    }

    /**
     * Create a physicsbody for this node, with the given mass.
     * Mass > 0 creates a dynamic body, mass = 0 creates a static body
     * @param mass the mass of the body
     */
    public abstract void createPhysics(float mass);

    /**
     * @return the physicsBody
     */
    public RigidBody getPhysicsBody() {
        return physicsBody;
    }

    /**
     * @param physicsBody the physicsBody to set
     */
    public void setPhysicsBody(RigidBody physicsBody) {
        this.physicsBody = physicsBody;
    }

    public float[] getTransformMatrix() {
        GL11.glPushMatrix();
        GL11.glLoadIdentity();

        GL11.glTranslated(getPosition().getX(), getPosition().getY(), getPosition().getZ());

        AxisAngle axisAngle = getRotation().toAxisAngle();
        GL11.glRotatef((float)axisAngle.getAngle(), (float)axisAngle.getAxis().getX(), (float)axisAngle.getAxis().getY(), (float)axisAngle.getAxis().getZ());

        //GL11.glScaled(getScale().getX(), getScale().getY(), getScale().getZ());

        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(4*4);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrixBuffer);
        GL11.glPopMatrix();

        float[] matrix = new float[4*4];
        matrixBuffer.get(matrix);

        System.out.println(""+matrix);

        return matrix;
    }

    /**
     * @return the actuator
     */
    public Actuator getActuator() {
        return actuator;
    }

    /**
     * @param actuator the actuator to set
     */
    public void setActuator(Actuator actuator) {
        this.actuator = actuator;
    }

    /**
     * @return the textures
     */
    public List<Texture> getTextures() {
        return textures;
    }
}
