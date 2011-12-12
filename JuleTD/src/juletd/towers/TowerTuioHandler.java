/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juletd.towers;

import juletd.towers.AbstractTower;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import java.util.HashMap;
import java.util.Map;
import juletd.World;

/**
 *
 * @author Rolf
 */
public class TowerTuioHandler implements TuioListener {
    private final World td;

    private Map<Integer, AbstractTower> towerMap;
    
    public TowerTuioHandler(World td) {
        this.td = td;
        
        towerMap = new HashMap<>();
    }
    
    @Override
    public void addTuioObject(TuioObject tobj) {
        createIfUnknown(tobj);
        
        towerMap.get(tobj.getSymbolID()).setVisible(true);
    }

    @Override
    public void updateTuioObject(TuioObject tobj) {
        createIfUnknown(tobj);
        
        towerMap.get(tobj.getSymbolID()).setPosition(tobj.getPosition().getX()*td.getWidth(), tobj.getPosition().getY()*td.getHeight());
    }

    @Override
    public void removeTuioObject(TuioObject tobj) {
        createIfUnknown(tobj);
        
        towerMap.get(tobj.getSymbolID()).setVisible(false);
    }

    @Override
    public void addTuioCursor(TuioCursor tcur) {
    }

    @Override
    public void updateTuioCursor(TuioCursor tcur) {
    }

    @Override
    public void removeTuioCursor(TuioCursor tcur) {
    }

    @Override
    public void refresh(TuioTime ftime) {
    }

    private void createIfUnknown(TuioObject tobj) {
        if(!towerMap.containsKey(tobj.getSymbolID())) {
            AbstractTower t = new BasicTower();
            towerMap.put(tobj.getSymbolID(), t);
            td.addTower(t);
        }
    }
}
