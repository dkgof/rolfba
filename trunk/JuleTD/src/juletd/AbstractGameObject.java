/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd;

import processing.core.PVector;

/**
 *
 * @author Rolf
 */
public abstract class AbstractGameObject implements GameObject {
    protected PVector position;
    
    public AbstractGameObject() {
        position = new PVector();
    }
    
    public void setPosition(float x, float y) {
        position.set(x, y, 0);
    }

    public void setPosition(PVector newPos) {
        position.set(newPos);
    }
    
    public PVector getPosition() {
        return position;
    }
    
    public String toString() {
        return "["+this.getClass()+"] - "+position;
    }
}
