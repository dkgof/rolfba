/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rolf
 */
public class DealFinder {
    public static List<Deal> findDeals(List<Order> fromOrders, List<Order> toOrders) {
        List<Deal> foundDeals = new LinkedList<Deal>();
        
        //sort from orders lowest first
        Collections.sort(fromOrders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
        
        //sort to orders highest first
        Collections.sort(toOrders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o2.getPrice(), o1.getPrice());
            }
        });

        for(Order fromOrder : fromOrders) {
            for(Order toOrder : toOrders) {
                if(fromOrder.getItemType() == toOrder.getItemType()) {
                    double maxVolumeBasedOnCost = Settings.singleton().getMaxCost() / fromOrder.getPrice();
                    double maxVolumeBasedOnSpace = Settings.singleton().getMaxVolume() / fromOrder.getSpacePerItem();
                    double possibleVolume = Math.min(Math.min(Math.min(fromOrder.getVolumeLeft(), toOrder.getVolumeLeft()), maxVolumeBasedOnCost), maxVolumeBasedOnSpace);
                    
                    Deal possibleDeal = new Deal(fromOrder, toOrder, possibleVolume);
                    
                    if(possibleDeal.getAssumedProfit() > Settings.singleton().getMinProfit()) {
                        foundDeals.add(possibleDeal);
                        fromOrder.addUsedVolume(possibleVolume);
                        toOrder.addUsedVolume(possibleVolume);
                    }
                }
            }
        }
        
        Collections.sort(foundDeals, new Comparator<Deal>() {

            @Override
            public int compare(Deal o1, Deal o2) {
                return Double.compare(o2.getAssumedProfit(), o1.getAssumedProfit());
            }
            
        });
        
        for(Deal d : foundDeals) {
            try {
                d.checkForScams();
            } catch (SQLException ex) {
                Logger.getLogger(DealFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return foundDeals;
    }
}
