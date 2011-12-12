/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * A GameObject is an element that exists within a World and can be drawn and updated
 * @author Rolf
 */
public interface GameObject {
    /**
     * Update this object
     * @param applet the applet the object is inside
     * @param deltaTime the time since last update
     */
    public void update(PApplet applet, float deltaTime);
    
    /**
     * Draw this object
     * @param applet the applet the object is inside
     */
    public void draw(PApplet applet);

    /**
     * Set the position of this object
     * @param x the x position to set
     * @param y the y position to set
     */
    public void setPosition(float x, float y);

    /**
     * Set the position of this object
     * @param newPos the PVector position to set
     */
    public void setPosition(PVector newPos);
    
    /**
     * Retrieve the position of this object as a PVector
     * @return the current position of this object
     */
    public PVector getPosition();
}
