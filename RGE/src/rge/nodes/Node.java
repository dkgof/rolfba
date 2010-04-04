package rge.nodes;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import rge.math.AxisAngle;
import rge.math.Quaternion;
import rge.math.Vector3;

/**
 * A node is the basic component of the scene graph
 * @author Rolf
 */
public abstract class Node {

    //The children of this node in the scene graph
    private List<Node> children;

    //The current position of this node
    private Vector3 position;

    //The current scale of this node
    private Vector3 scale;

    //The current rotation of this node
    private Quaternion rotation;

    public Node() {
        children = new ArrayList<Node>();
        position = new Vector3(0,0,0);
        scale = new Vector3(1,1,1);
        rotation = Quaternion.createFromAxisAngle(0, Vector3.UnitX);
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

        this.render();

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
    public void translate(double x, double y, double z) {
        setPosition(new Vector3(x, y, z));
    }

    /**
     * Scales this node with the given values on each axis.
     * @param x the amount to scale on the x axis
     * @param y the amount to scale on the y axis
     * @param z the amount to scale on the z axis
     */
    public void scale(double x, double y, double z) {
        setScale(new Vector3(x, y, z));
    }

    /**
     * Scales this node uniformly on all 3 axis with the given scale
     * @param scale the amount to scale
     */
    public void scale(double scale) {
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
}
