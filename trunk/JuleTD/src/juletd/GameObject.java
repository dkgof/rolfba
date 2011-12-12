/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd;

import java.awt.geom.Point2D;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author Rolf
 */
public interface GameObject {
    public void update(PApplet applet, float deltaTime);
    
    public void draw(PApplet applet);

    public void setPosition(float x, float y);

    public void setPosition(PVector newPos);
    
    public PVector getPosition();
}
