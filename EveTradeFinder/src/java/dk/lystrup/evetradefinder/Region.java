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

    public Map<Long,List<Order>> getOrders(Order.OrderType type) throws SQLException {
        Map<Long,List<Order>> foundOrders = new HashMap<Long,List<Order>>();
        
        Statement stm = Database.singleton().createStatement();
        
        
        ResultSet rs = stm.executeQuery("SELECT * from marketorders JOIN (stastations,invtypes) ON marketorders.stationId = stastations.stationID AND invtypes.typeID = marketorders.typeId WHERE stastations.regionID = "+this.id+" AND bid = "+((type == Order.OrderType.BUY)?1:0));
       
        while(rs.next()) {
            
            int groupId = rs.getInt("groupID");
            
            Order foundOrder = new Order(
                    rs.getLong("id"),
                    rs.getLong("typeId"),
                    rs.getDouble("volume"),
                    rs.getDouble("minVolume"),
                    rs.getDouble("price"),
                    rs.getLong("stationId"),
                    rs.getInt("bid")==1,
                    Order.getSpaceFromGroupId(groupId, rs.getDouble("volume"))
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
        
        //ResultSet rs = stm.executeQuery("SELECT regionID, regionName FROM mapregions");
        ResultSet rs = stm.executeQuery("SELECT mapregions.regionID, mapregions.regionName FROM mapregions JOIN (marketorders, stastations) ON marketorders.stationId = stastations.stationID AND mapregions.regionID = stastations.regionID GROUP BY mapregions.regionID");
        
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
