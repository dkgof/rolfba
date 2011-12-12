/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.mobs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import juletd.GameObject;

/**
 *
 * @author Rolf
 */
public class MobSpawner {
    
    private Class mobType;
    
    public MobSpawner() {
        mobType = BasicMob.class;
    }
    
    public void setMobType(Class newType) {
        if(AbstractMob.class.isAssignableFrom(newType)) {
            mobType = newType;
        }
    }
    
    public AbstractMob createMob() {
        try {
            Constructor constructor = mobType.getConstructor();
            return (AbstractMob)constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            ex.printStackTrace();
        }
        
        throw new IllegalStateException("Could not create mob of current type: "+mobType.getCanonicalName());
    }
}
