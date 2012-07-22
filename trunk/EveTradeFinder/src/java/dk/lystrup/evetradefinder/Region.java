/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rolf
 */
public class Region {
    private String name;
    private long id;
    
    public Region(long id) throws SQLException {
        this(id, Database.singleton().lookupRegionName(id));
    }

    public Region(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<SolarSystem> getAllSolarSystems() throws SQLException {
        List<SolarSystem> systems = new LinkedList<SolarSystem>();
        
        Statement stm = Database.singleton().createStatement();
        ResultSet rs = stm.executeQuery("SELECT solarSystemID, solarSystemName FROM mapsolarsystems WHERE regionID like "+this.id);
        
        while(rs.next()) {
            long id = rs.getLong("solarSystemID");
            String name = rs.getString("solarSystemName");

            systems.add(new SolarSystem(id, name));
        }
        
        return systems;
    }
    
    public static Region getRegionFromName(String regionName) throws SQLException {
        Region foundRegion = null;
        
        Statement stm = Database.singleton().createStatement();
        
        ResultSet rs = stm.executeQuery("SELECT regionID, regionName FROM mapregions WHERE regionName like '"+regionName+"'");
        
        rs.first();
        long id = rs.getLong("regionID");
        String name = rs.getString("regionName");
        
        foundRegion = new Region(id, name);
        
        stm.close();
        
        return foundRegion;
    }

    public static List<Region> getAll() throws SQLException {
        List<Region> foundRegions = new LinkedList<Region>();
        
        Statement stm = Database.singleton().createStatement();
        
        ResultSet rs = stm.executeQuery("SELECT regionID, regionName FROM mapregions");
        
        while(rs.next()) {
            long id = rs.getLong("regionID");
            String name = rs.getString("regionName");

            foundRegions.add(new Region(id, name));
        }
        
        stm.close();
        
        return foundRegions;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
}
