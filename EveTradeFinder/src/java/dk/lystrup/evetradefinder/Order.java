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

    public static double getSpaceFromGroupId(int groupId, double dbVolume) {
        double space;
        switch(groupId) {
            case 485:
            case 659:
            case 883:
            case 513:
            case 902:
                //Capital
                space = 1000000;
                break;
                
            case 941:
                //Orca
                space = 500000;
                break;

            case 30:
                //Titan
                space = 10000000;
                break;
            
            case 419:
            case 540:
                //Battlecruiser
                space = 15000;
                break;

            case 26:
            case 832:
            case 358:
            case 894:
            case 906:
                //Cruiser
                space = 10000;
                break;
                
            case 963:
                //Strategic Cruiser
                space = 5000;
                break;

            case 31:
                //Shuttle
                space = 5000;
                break;

            case 543:
            case 463:
                //Barge
                space = 3750;
                break;

            case 28:
            case 380:
                //Industrial
                space = 20000;
                break;

            case 25:
            case 324:
            case 830:
            case 893:
            case 831:
                //Frigate
                space = 2500;
                break;

            case 541:
            case 420:
                //Destroyer
                space = 5000;
                break;

            case 27:
            case 898:
            case 900:
                //Battleship
                space = 50000;
                break;
                
            default:
                space = dbVolume;
                break;
        }
        
        return space;
    }

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

    /**
     * @return the minVolume
     */
    public double getMinVolume() {
        return minVolume;
    }

    public enum OrderType {
        SELL,
        BUY
    }
    
    private long id;
    private long itemType;
    private double volume;
    private final double minVolume;
    private double price;
    private long stationId;
    private boolean bid;
    private double spacePerItem;
    
    private double usedVolume;
    
    public Order(long id, long itemType, double volume, double minVolume, double price, long stationId, boolean bid, double spacePerItem) throws SQLException {
        this.id = id;
        this.itemType = itemType;
        this.volume = volume;
        this.minVolume = minVolume;
        this.price = price;
        this.stationId = stationId;
        this.bid = bid;
        this.spacePerItem = spacePerItem;
        
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
            PreparedStatement stm = Database.singleton().createPreparedStatement("REPLACE INTO marketorders (id,typeId,stationId,volume,price,bid,minVolume) VALUES (?,?,?,?,?,?,?)");
            
            stm.setLong(1, id);
            stm.setLong(2, itemType);
            stm.setLong(3, stationId);
            stm.setDouble(4, volume);
            stm.setDouble(5, price);
            stm.setInt(6, bid?1:0);
            stm.setDouble(7, minVolume);
            
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
