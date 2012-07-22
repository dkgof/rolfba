/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rolf
 */
public class Deal {
    private Order fromOrder;
    private Order toOrder;

    private double amount;
    
    public Deal(Order from, Order to, double amount) {
        this.fromOrder = from;
        this.toOrder = to;
        this.amount = amount;
    }
    
    public double getAssumedProfit() {
        double cost = fromOrder.getPrice() * amount;
        double tax = cost * Settings.singleton().getBrokerFee() + cost * Settings.singleton().getStationTax();
        double income = toOrder.getPrice() * amount;
        
        return income - cost - tax;
    }
    
    public double getSpaceNeeded() throws SQLException {
        return Database.singleton().lookupSpace(fromOrder.getItemType()) * amount;
    }
    
    public double getProfitPerUnit() {
        return getAssumedProfit() / amount;
    }
    
    public double getProfitFraction() {
        return getAssumedProfit() / (fromOrder.getPrice() * amount);
    }
    
    public String getFormattedHTML() throws SQLException {
        String html = "";
        
        html += "<div class=\"order\">";
        
        html += "<div class=\"item\">"+Database.singleton().lookupItemName(fromOrder.getItemType())+"</div>";
        html += "<div class=\"station\">From: "+Database.singleton().lookupStationName(fromOrder.getStationId())+"</div>";
        html += "<div class=\"station\">To: "+Database.singleton().lookupStationName(toOrder.getStationId())+"</div>";
        
        double minAmount = toOrder.getMinVolume();
        
        html += "<div class=\"details\">";
        html += "<div class=\"amount\">Amount: "+String.format("%.1f",amount)+"</div>";
        html += "<div class=\"minVolume"+((minAmount>1)?" alert":"")+"\">Min Amount: "+String.format("%.1f",minAmount)+"</div>";
        html += "<div class=\"profit\">Profit: <div>"+String.format("%.1fM",this.getAssumedProfit()/1000000)+"</div></div>";
        html += "<div class=\"profitUnit\">Profit/Unit: "+String.format("%.1f",this.getProfitPerUnit())+"</div>";
        html += "<div class=\"invest\">Invest: "+String.format("%.1fM",fromOrder.getPrice()*amount / 1000000)+"</div>";
        html += "<div class=\"fraction\">Fraction: "+String.format("%.1f",this.getProfitFraction()*100)+"%</div>";
        html += "<div class=\"volume\">"+String.format("%.1fm&sup3;",this.getSpaceNeeded())+"</div>";
        html += "</div>";
        
        html += "</div>";

        return html;
    }
    
    @Override
    public String toString() {
        try {
            return "<div>Buy "+amount+" x "+Database.singleton().lookupItemName(fromOrder.getItemType())+" from "+Database.singleton().lookupStationName(fromOrder.getStationId())+" to "+Database.singleton().lookupStationName(toOrder.getStationId())+" profit "+String.format("%.1fM isk",getAssumedProfit()/1000000.0);
        } catch (SQLException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Error with deal toString";
    }
}
