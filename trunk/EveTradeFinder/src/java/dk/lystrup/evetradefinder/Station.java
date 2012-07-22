/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import dk.lystrup.evetradefinder.Order.OrderType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rolf
 */
public class Station {
    private long id;
    private String name;
    
    public Station(long id) throws SQLException {
        this(id, Database.singleton().lookupStationName(id));
    }
    
    public Station(long id, String name) {
        this.id = id;
        this.name = name;
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
    
    public List<Order> getOrders(OrderType type) throws SQLException {
        List<Order> foundOrders = new LinkedList<Order>();
        
        Statement stm = Database.singleton().createStatement();
        
        ResultSet rs = stm.executeQuery("SELECT * FROM marketorders JOIN invtypes ON invtypes.typeID = marketorders.typeId WHERE stationId = "+this.getId()+" AND bid = "+((type == OrderType.BUY)?1:0));
        
        while(rs.next()) {
            int groupId = rs.getInt("groupID");
            
            Order foundOrder = new Order(
                    rs.getLong("id"),
                    rs.getLong("marketorders.typeId"),
                    rs.getDouble("volume"),
                    rs.getDouble("minVolume"),
                    rs.getDouble("price"),
                    rs.getLong("stationId"),
                    rs.getInt("bid")==1,
                    Order.getSpaceFromGroupId(groupId, rs.getDouble("volume"))
                    );
            
            foundOrders.add(foundOrder);
        }
        
        return foundOrders;
    }

    public List<Order> getOrders(OrderType orderType, long itemType) throws SQLException {
        List<Order> foundOrders = new LinkedList<Order>();
        
        Statement stm = Database.singleton().createStatement();
        
        ResultSet rs = stm.executeQuery("SELECT * FROM marketorders JOIN invtypes ON invtypes.typeID = marketorders.typeId WHERE stationId = "+this.getId()+" AND bid = "+((orderType == OrderType.BUY)?1:0)+" AND marketorders.typeId = "+itemType);
        
        while(rs.next()) {
            int groupId = rs.getInt("groupID");
            
            Order foundOrder = new Order(
                    rs.getLong("id"),
                    rs.getLong("marketorders.typeId"),
                    rs.getDouble("volume"),
                    rs.getDouble("minVolume"),
                    rs.getDouble("price"),
                    rs.getLong("stationId"),
                    rs.getInt("bid")==1,
                    Order.getSpaceFromGroupId(groupId, rs.getDouble("volume"))
                    );
            
            foundOrders.add(foundOrder);
        }
        
        return foundOrders;
    }
}
