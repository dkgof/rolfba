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
@Data public class Builder {
    //Name of the ingame char
    private String name;
    
    //Negotiate level of the builder
    private int negotiateLevel;

    //Levels of the different build skills
    private int buildShieldLevel;
    private int buildDeviceLevel;
    private int buildWeaponLevel;
    private int buildReactorLevel;
    private int buildComponentLevel;
}
