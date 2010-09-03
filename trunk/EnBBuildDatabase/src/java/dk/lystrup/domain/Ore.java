/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.domain;

import lombok.Data;

/**
 *
 * @author Rolf
 */
@Data public class Ore {
    //The name of the ore
    private String name;
    
    //The level of the ore
    private int level;

    //Refining cost of the ore
    private int refineCost;

    //Ammount of ore needed to refine 1 item
    private int refineAmount;
}
