/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.mobs;

/**
 * Models a mob (Enemy) in the World
 * @author Rolf
 */
public interface Mob {
    /**
     * Retrieve the current health of this mob
     * @return the current health of this mob
     */
    public float getHealth();
    
    /**
     * Set the current health of this mob
     * @param health the health to set
     */
    public void setHealth(float health);
}
