/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.mobs;

import juletd.AbstractGameObject;
import processing.core.PApplet;

/**
 *
 * @author Rolf
 */
public abstract class AbstractMob extends AbstractGameObject implements Mob {

    private float health;
    protected float speed;
    
    @Override
    public void update(PApplet applet, float deltaTime) {
        randomWalk(applet, deltaTime);
    }

    @Override
    public void draw(PApplet applet) {
        doDraw(applet);
    }

    protected abstract void doDraw(PApplet applet);

    private void randomWalk(PApplet applet, float deltaTime) {
        position.set(position.x+applet.random(-1, 1)*speed*deltaTime, position.y+applet.random(-1, 1)*speed*deltaTime,0);
    }

    /**
     * @return the health
     */
    public float getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(float health) {
        this.health = health;
    }
    
}
