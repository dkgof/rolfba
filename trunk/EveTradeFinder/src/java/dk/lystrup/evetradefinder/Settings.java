/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Rolf
 */
public class Settings {
    private static Settings singleton;

    private double minProfit;
    private double maxVolume;
    private double maxCost;
    private double brokerFee;
    private double stationTax;
    
    private Settings() {
        minProfit = 100000;
        maxVolume = 1000;
        maxCost = 10000000;
        brokerFee = 0.015;
        stationTax = 0.012;
    }
    
    public static Settings singleton() {
        if(singleton == null) {
            singleton = new Settings();
        }
        
        return singleton;
    }

    /**
     * @return the minProfit
     */
    public double getMinProfit() {
        return minProfit;
    }

    /**
     * @param minProfit the minProfit to set
     */
    public void setMinProfit(double minProfit) {
        this.minProfit = minProfit;
    }

    /**
     * @return the maxVolume
     */
    public double getMaxVolume() {
        return maxVolume;
    }

    /**
     * @param maxVolume the maxVolume to set
     */
    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    /**
     * @return the maxCost
     */
    public double getMaxCost() {
        return maxCost;
    }

    /**
     * @param maxCost the maxCost to set
     */
    public void setMaxCost(double maxCost) {
        this.maxCost = maxCost;
    }

    /**
     * @return the brokerFee
     */
    public double getBrokerFee() {
        return brokerFee;
    }

    /**
     * @param brokerFee the brokerFee to set
     */
    public void setBrokerFee(double brokerFee) {
        this.brokerFee = brokerFee;
    }

    /**
     * @return the stationTax
     */
    public double getStationTax() {
        return stationTax;
    }

    /**
     * @param stationTax the stationTax to set
     */
    public void setStationTax(double stationTax) {
        this.stationTax = stationTax;
    }
}
