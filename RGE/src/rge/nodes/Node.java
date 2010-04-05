package rge.nodes;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
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

    //The current position of this node
    private Vector3 position;

    //The current scale of this node
    private Vector3 scale;

    //The current rotation of this node
    private Quaternion rotation;

    private List<Texture> textures;

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

        GL11.glTranslated(getPosition().getX(), getPosition().getY(), getPosition().getZ());

        GL11.glScaled(getScale().getX(), getScale().getY(), getScale().getZ());

        AxisAngle aa = getRotation().toAxisAngle();

        GL11.glRotatef((float)aa.getAngle(), (float)aa.getAxis().getX(), (float)aa.getAxis().getY(), (float)aa.getAxis().getZ());

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
    public abstract void update(double deltaTime);

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
        for(Texture tex : textures) {
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
        textures.clear();
        textures.add(tex);
    }

    /**
     * Add another texture to this node
     * @param tex the texture to add
     */
    public void addTexture(Texture tex) {
        textures.add(tex);
    }
}
