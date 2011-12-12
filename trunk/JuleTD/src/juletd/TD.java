/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd;

import juletd.towers.TowerTuioHandler;
import processing.core.*;
import TUIO.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import juletd.mobs.AbstractMob;
import juletd.mobs.Mob;
import juletd.mobs.MobSpawner;
import juletd.towers.projectiles.Projectile;

/**
 *
 * @author Rolf
 */
public class TD extends PApplet implements TuioListener {

    private static TD singleton;
    
    private final List<TuioListener> tuioListeners;
    private final List<GameObject> projectiles;
    private final List<GameObject> towers;
    private final List<GameObject> mobs;
    
    private final MobSpawner spawner;

    private TuioProcessing tuioClient;

    private int last;

    public TD() {
        tuioListeners = new ArrayList<>();
        
        projectiles = new CopyOnWriteArrayList<>();
        mobs = new CopyOnWriteArrayList<>();
        towers = new CopyOnWriteArrayList<>();
        
        spawner = new MobSpawner();
        
        tuioListeners.add(new TowerTuioHandler(this));
        
        singleton = this;
    }

    public static TD getTD() {
        return singleton;
    }
    
    public void addTuioListener(TuioListener listener) {
        tuioListeners.add(listener);
    }
    
    @Override
    public void setup() {
        tuioClient = new TuioProcessing(this);

        size(640, 480, OPENGL);
        smooth();
        
        colorMode(RGB);
        
        last = 0;
    }

    @Override
    public void draw() {
        background(255);
        int now = millis();
        
        float delta = (now-last) / 1000.0f;
        
        //5% chance to spawn a mob
        if(random(100) >= 95) {
            PVector position = new PVector(TD.getTD().random(0,TD.getTD().getWidth()), TD.getTD().random(0,TD.getTD().getHeight()));
            AbstractMob m = spawner.createMob(position);
            addMob(m);
        }

        updateGameObjects(delta);
        drawGameObjects();
        
        checkForCollisions();
        
        last = now;
    }

    public List<AbstractMob> getMobsInRange(PVector point, double range) {
        List<AbstractMob> foundMobs = new ArrayList<>();
        
        for(GameObject go : mobs) {
            if(AbstractMob.class.isAssignableFrom(go.getClass())) {
                AbstractMob am = (AbstractMob) go;
                
                if(am.getPosition().dist(point) <= range) {
                    foundMobs.add(am);
                }
            }
        }
        
        return foundMobs;
    }
    
    // <editor-fold defaultstate="collapsed" desc="TUIO Handling">
    @Override
    public void addTuioCursor(TuioCursor cur) {
        for (TuioListener listener : tuioListeners) {
            listener.addTuioCursor(cur);
        }
    }

    @Override
    public void addTuioObject(TuioObject obj) {
        for (TuioListener listener : tuioListeners) {
            listener.addTuioObject(obj);
        }
    }

    @Override
    public void refresh(TuioTime time) {
        for (TuioListener listener : tuioListeners) {
            listener.refresh(time);
        }
    }

    @Override
    public void updateTuioObject(TuioObject obj) {
        for (TuioListener listener : tuioListeners) {
            listener.updateTuioObject(obj);
        }
    }

    @Override
    public void removeTuioObject(TuioObject obj) {
        for (TuioListener listener : tuioListeners) {
            listener.removeTuioObject(obj);
        }
    }

    @Override
    public void updateTuioCursor(TuioCursor cur) {
        for (TuioListener listener : tuioListeners) {
            listener.updateTuioCursor(cur);
        }
    }

    @Override
    public void removeTuioCursor(TuioCursor cur) {
        for (TuioListener listener : tuioListeners) {
            listener.removeTuioCursor(cur);
        }
    }
    // </editor-fold>
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Start the processing applet
        PApplet.main(new String[]{"juletd.TD"});
    }

    private void updateGameObjects(float delta) {
        for(GameObject go : towers) {
            go.update(this, delta);
        }
        for(GameObject go : projectiles) {
            go.update(this, delta);
        }
        for(GameObject go : mobs) {
            go.update(this, delta);
        }
    }

    private void drawGameObjects() {
        for(GameObject go : towers) {
            go.draw(this);
        }
        for(GameObject go : mobs) {
            go.draw(this);
        }
        for(GameObject go : projectiles) {
            go.draw(this);
        }
    }
    
    public void addTower(GameObject go) {
        towers.add(go);
    }

    public void removeTower(GameObject go) {
        towers.remove(go);
    }

    public void addProjectile(GameObject go) {
        projectiles.add(go);
    }

    public void removeProjectile(GameObject go) {
        projectiles.remove(go);
    }

    public void addMob(GameObject go) {
        mobs.add(go);
    }

    public void removeMob(GameObject go) {
        mobs.remove(go);
    }

    private void checkForCollisions() {
        for(GameObject projObj : projectiles) {
            for(GameObject mobObj : mobs) {
                
                if(projObj.getPosition().dist(mobObj.getPosition()) < 5) {
                    Projectile proj = (Projectile) projObj;
                    Mob mob = (Mob) mobObj;
                    
                    mob.setHealth(mob.getHealth() - proj.getDamage());
                    
                    if(mob.getHealth() <= 0) {
                        removeMob(mobObj);
                    }
                    
                    removeProjectile(projObj);
                }
            }
        }
    }
}
