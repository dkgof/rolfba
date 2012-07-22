/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rolf
 */
public class Order {

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the itemType
     */
    public long getItemType() {
        return itemType;
    }

    /**
     * @return the volume
     */
    public double getVolume() {
        return volume;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the stationId
     */
    public long getStationId() {
        return stationId;
    }

    /**
     * @return the bid
     */
    public boolean isBid() {
        return bid;
    }

    public double getSpacePerItem() {
        return spacePerItem;
    }

    public enum OrderType {
        SELL,
        BUY
    }
    
    private long id;
    private long itemType;
    private double volume;
    private double price;
    private long stationId;
    private boolean bid;
    private double spacePerItem;
    
    private double usedVolume;
    
    public Order(long id, long itemType, double volume, double price, long stationId, boolean bid) throws SQLException {
        this.id = id;
        this.itemType = itemType;
        this.volume = volume;
        this.price = price;
        this.stationId = stationId;
        this.bid = bid;
        spacePerItem = Database.singleton().lookupSpace(itemType);
        
        usedVolume = 0;
    }

    public double getVolumeLeft() {
        return volume - usedVolume;
    }
    
    public void addUsedVolume(double volume) {
        usedVolume += volume;
    }
    
    public void resetUsedVolume() {
        usedVolume = 0;
    }
    
    private OrderType getOrderType() {
        return bid?OrderType.BUY:OrderType.SELL;
    }
    
    public void save() {
        try {
            PreparedStatement stm = Database.singleton().createPreparedStatement("REPLACE INTO marketorders (id,typeId,stationId,volume,price,bid) VALUES (?,?,?,?,?,?)");
            
            stm.setLong(1, id);
            stm.setLong(2, itemType);
            stm.setLong(3, stationId);
            stm.setDouble(4, volume);
            stm.setDouble(5, price);
            stm.setInt(6, bid?1:0);
            
            stm.executeUpdate();
            
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() {
        try {
            return getOrderType()+" Order: ["+Database.singleton().lookupItemName(itemType)+"] x "+volume+" at price "+price+" isk - at station: "+Database.singleton().lookupStationName(stationId);
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
            return "Error while fetching from database...";
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Order) {
            Order ord = (Order)o;
            
            return ord.id == this.id;
        }
        
        return false;
    }
}
