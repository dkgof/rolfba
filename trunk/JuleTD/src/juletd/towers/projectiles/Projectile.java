/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.towers.projectiles;

/**
 * Models a projectile in the World, projectiles can collide with Mobs
 * @author Rolf
 */
public interface Projectile {
    /**
     * Retrieve the damage this projectile does
     * @return the damage of this projectile
     */
    public float getDamage();
}
