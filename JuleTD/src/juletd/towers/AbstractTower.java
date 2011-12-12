/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.towers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import juletd.AbstractGameObject;
import juletd.GameObject;
import juletd.World;
import juletd.mobs.AbstractMob;
import juletd.towers.projectiles.AbstractProjectile;
import processing.core.PApplet;

/**
 *
 * @author Rolf
 */
public abstract class AbstractTower extends AbstractGameObject implements Tower {
    
    private boolean visible;
    protected float range;
    protected float reloadTime;
    
    protected float timeUntilShot;
    
    protected Class projectileClass;
    
    public AbstractTower() {
        range = 0;
        reloadTime = Float.MAX_VALUE;
        timeUntilShot = 0;
        projectileClass = null;
        visible = false;
    }

    @Override
    public void update(PApplet applet, float deltaTime) {
        if(visible) {
            timeUntilShot -= deltaTime;

            if(timeUntilShot <= 0) {

                List<AbstractMob> mobsInRange = World.getTD().getMobsInRange(position, range);

                if(!mobsInRange.isEmpty()) {
                    AbstractMob randomMob = mobsInRange.get(applet.parseInt(applet.random(mobsInRange.size())));
                    shootAt(randomMob);
                }
            }
        }
    }

    @Override
    public void draw(PApplet applet) {
        if(visible) {
            doDraw(applet);
            
            drawRangeCircle(applet);
        }
    }

    public void setVisible(boolean b) {
        visible = b;
    }
    
    public boolean isVisible() {
        return visible;
    }

    protected abstract void doDraw(PApplet applet);

    private void drawRangeCircle(PApplet applet) {
        applet.noFill();
        applet.stroke(255,0,0);
        applet.ellipse(position.x, position.y, range*2, range*2);
    }

    private void shootAt(AbstractMob randomMob) {
        try {
            Constructor projectileConstructor = projectileClass.getConstructor(GameObject.class, GameObject.class);
            AbstractProjectile projectile = (AbstractProjectile) projectileConstructor.newInstance(this, randomMob);
            
            World.getTD().addProjectile(projectile);
            
            timeUntilShot = reloadTime;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            ex.printStackTrace();
        }
    }
}
