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
    private String name;
    private int level;
    private int negotiateLevel;
    private int buildShieldLevel;
    private int buildDeviceLevel;
    private int buildWeaponLevel;
    private int buildReactorLevel;
    private int buildComponentLevel;
}
