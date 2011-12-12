/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.towers.projectiles;

import juletd.GameObject;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author Rolf
 */
public class BasicProjectile extends AbstractProjectile {

    private PVector direction;
    
    public BasicProjectile(GameObject shooter, GameObject target) {
        super(shooter, target);
        
        setPosition(shooter.getPosition());
        
        speed = 50;
        duration = 10;
        damage = 1;
        
        direction = PVector.sub(target.getPosition(), position);
        direction.normalize();
    }
    
    @Override
    protected void doUpdate(PApplet applet, float deltaTime) {
        position.add(PVector.mult(direction, deltaTime*speed));
    }

    @Override
    protected void doDraw(PApplet applet) {
        applet.fill(0,255,255);
        applet.stroke(0,255,0);
        applet.ellipse(position.x, position.y, 2, 2);
    }
    
}
