/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rolf
 */
public class SolarSystem {
    private String name;
    private long id;
    
    public SolarSystem(long id, String name) {
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
        ResultSet rs = stm.executeQuery("SELECT mapsolarsystems.solarSystemID, mapsolarsystems.solarSystemName FROM mapsolarsystems JOIN (marketorders, stastations) ON marketorders.stationId = stastations.stationID AND mapsolarsystems.solarSystemID = stastations.solarSystemID GROUP BY mapsolarsystems.solarSystemID");
        
        while(rs.next()) {
            long id = rs.getLong("mapsolarsystems.solarSystemID");
            String name = rs.getString("mapsolarsystems.solarSystemName");

            systems.add(new SolarSystem(id, name));
        }
        
        return systems;
    }
    
    public Map<Long,List<Order>> getOrders(Order.OrderType type) throws SQLException {
        Map<Long,List<Order>> foundOrders = new HashMap<Long,List<Order>>();
        
        Statement stm = Database.singleton().createStatement();
        
        
        ResultSet rs = stm.executeQuery("SELECT * from marketorders JOIN stastations ON marketorders.stationId = stastations.stationID WHERE stastations.solarSystemID = "+this.id+" AND bid = "+((type == Order.OrderType.BUY)?1:0));
        
        while(rs.next()) {
            
            Order foundOrder = new Order(
                    rs.getLong("id"),
                    rs.getLong("typeId"),
                    rs.getDouble("volume"),
                    rs.getDouble("minVolume"),
                    rs.getDouble("price"),
                    rs.getLong("stationId"),
                    rs.getInt("bid")==1
                    );
            
            List<Order> orderList = foundOrders.get(foundOrder.getItemType());
            if(orderList == null) {
                orderList = new LinkedList<Order>();
                foundOrders.put(foundOrder.getItemType(), orderList);
            }
            orderList.add(foundOrder);
        }
        
        return foundOrders;
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
