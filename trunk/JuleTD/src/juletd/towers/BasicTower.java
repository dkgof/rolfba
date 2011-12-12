/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.towers;

import juletd.towers.projectiles.BasicProjectile;
import processing.core.PApplet;

/**
 *
 * @author Rolf
 */
public class BasicTower extends AbstractTower {

    public BasicTower() {
        range = 100f;
        reloadTime = 0.7f;
        projectileClass = BasicProjectile.class;
    }
    
    @Override
    protected void doDraw(PApplet applet) {
        applet.fill(0);
        applet.noStroke();
        applet.ellipse(position.x, position.y, 20, 20);
    }
}
