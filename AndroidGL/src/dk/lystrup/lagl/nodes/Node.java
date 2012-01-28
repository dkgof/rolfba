/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl.nodes;

import dk.lystrup.lagl.actuators.Actuator;
import dk.lystrup.lagl.math.Quaternion;
import dk.lystrup.lagl.math.Vector3;
import dk.lystrup.lagl.textures.Texture;

/**
 * This interface represents a Node in the scene graph, it can be rendered
 * positioned, rotated and scaled.
 *
 * Since its a node in the graph, it can be attached to another node, or have
 * nodes attached to it.
 *
 * @author Rolf
 */
public interface Node {
    /**
     * Recursively render this node and all its children
     */
    public void recursiveRender();

    /**
     * Recursively call the update method on all children to allow the actuator
     * system to know that time has passed.
     * @param deltaTime 
     */
    public void recursiveUpdate(float deltaTime);

    /**
     * Return the position of this node as a Vector3
     * @return the Vector3 representing the position of this node
     */
    public Vector3 getPosition();

    /**
     * Set the position of this node to that of the given Vector3
     * @param position the position to set for this node.
     */
    public void setPosition(Vector3 position);

    /**
     * Sets the position of this node to the coordinates given
     * @param x
     * @param y
     * @param z 
     */
    public void setPosition(float x, float y, float z);

    /**
     * Return the Quaternion representing this Node's rotation
     * @return the Quaternion representing the rotation
     */
    public Quaternion getRotation();
    
    /**
     * Set the rotation of this Node to the Quaternion given as argument.
     * @param q the Quaternion to set this Node's rotation too.
     */
    public void setRotation(Quaternion q);

    /**
     * Attaches the given Node to this Node as one if its children
     * @param n the Node to attach
     */
    public void attachNode(Node n);

    /**
     * Set the scale of this node to the same on all 3 axis
     * @param scale the scale to use on all 3 axis
     */
    public void setScale(float scale);
    
    /**
     * Sets individual scales for all 3 axis
     * @param x the x-axis scale
     * @param y the y-axis scale
     * @param z the z-axis scale
     */
    public void setScale(float x, float y, float z);
    
    public void setActuator(Actuator act);

    public void addTexture(Texture tex);

    public void setTexture(Texture tex);

    public void setTexture(Texture tex, int textureUnit);
}
