/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rolf
 */
public class DealFinder {
    private static final double MIN_PROFIT = 100000;
    
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
                    double possibleVolume = Math.min(fromOrder.getVolumeLeft(), toOrder.getVolumeLeft());
                    
                    Deal possibleDeal = new Deal(fromOrder, toOrder, possibleVolume);
                    
                    if(possibleDeal.getAssumedProfit() > MIN_PROFIT) {
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
        
        return foundDeals;
    }
}
