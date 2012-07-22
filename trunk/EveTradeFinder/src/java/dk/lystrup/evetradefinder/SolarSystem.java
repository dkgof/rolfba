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
public class SolarSystem {
    private String name;
    private long id;
    
    private SolarSystem(long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static SolarSystem getSystemFromName(String systemName) throws SQLException {
        SolarSystem foundSystem = null;
        
        Statement stm = Database.singleton().createStatement();
        
        ResultSet rs = stm.executeQuery("SELECT solarSystemID, solarSystemName FROM mapsolarsystems WHERE solarSystemName like '"+systemName+"'");
        
        rs.first();
        long id = rs.getLong("solarSystemID");
        String name = rs.getString("solarSystemname");
        
        foundSystem = new SolarSystem(id, name);
        
        stm.close();
        
        return foundSystem;
    }

    public List<Station> getStations() throws SQLException {
        List<Station> foundStations = new LinkedList<Station>();
        
        Statement stm = Database.singleton().createStatement();
        
        ResultSet rs = stm.executeQuery("SELECT stationID, stationName FROM stastations WHERE solarSystemID = "+this.getId());
        
        while(rs.next()) {
            foundStations.add(new Station(rs.getLong("stationID"), rs.getString("stationName")));
        }
        
        stm.close();
        
        return foundStations;
    }

    public static List<SolarSystem> getAll() throws SQLException {
        List<SolarSystem> systems = new LinkedList<SolarSystem>();
        
        Statement stm = Database.singleton().createStatement();
        ResultSet rs = stm.executeQuery("SELECT solarSystemID, solarSystemName FROM mapsolarsystems");
        
        while(rs.next()) {
            long id = rs.getLong("solarSystemID");
            String name = rs.getString("solarSystemName");

            systems.add(new SolarSystem(id, name));
        }
        
        return systems;
    }
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
}
