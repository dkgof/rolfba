/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.mobs;

import juletd.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author Rolf
 */
public class BasicMob extends AbstractMob {

    public BasicMob() {
        speed = 5;
    }
    
    @Override
    protected void doDraw(PApplet applet) {
        applet.rectMode(applet.CENTER);
        applet.noStroke();
        applet.fill(128,128,128);
        applet.rect(position.x, position.y, 10, 10);
    }
    
}
