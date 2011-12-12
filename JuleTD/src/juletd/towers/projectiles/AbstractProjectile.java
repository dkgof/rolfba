/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.towers.projectiles;

import juletd.AbstractGameObject;
import juletd.GameObject;
import juletd.TD;
import processing.core.PApplet;

/**
 *
 * @author Rolf
 */
public abstract class AbstractProjectile extends AbstractGameObject implements Projectile {
    protected float speed;
    protected float duration;
    protected float damage;
    
    private float alive;
    
    protected GameObject target;
    protected final GameObject shooter;
    
    public AbstractProjectile(GameObject shooter, GameObject target) {
        this.target = target;
        this.shooter = shooter;
        alive = 0;
        duration = 0;
        speed = 0;
        damage = 0;
    }

    @Override
    public void update(PApplet applet, float deltaTime) {
        alive += deltaTime;
        
        if(alive > duration) {
            //Projectile has lived long enough
            TD.getTD().removeProjectile(this);
        }
        
        doUpdate(applet, deltaTime);
    }

    public void draw(PApplet applet) {
        doDraw(applet);
    }
    
    public float getDamage() {
        return damage;
    }
    
    protected abstract void doUpdate(PApplet applet, float deltaTime);

    protected abstract void doDraw(PApplet applet);
}
