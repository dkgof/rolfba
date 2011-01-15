/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl.nodes;

import dk.lystrup.androidgl.actuators.Actuator;
import dk.lystrup.androidgl.math.Quaternion;
import dk.lystrup.androidgl.math.Vector3;
import dk.lystrup.androidgl.textures.Texture;
import javax.microedition.khronos.opengles.GL10;

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
     * @param gl The OpenGL variable to use for rendering
     */
    public void recursiveRender(GL10 gl);

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

    public void setActuator(Actuator act);

    public void addTexture(Texture tex);

    public void setTexture(Texture tex);

    public void setTexture(Texture tex, int textureUnit);
}
