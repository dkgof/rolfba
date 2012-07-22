/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rolf
 */
public class Database {
    
    private static Database singleton;
    
    private static final String url = "jdbc:mysql://localhost:3306/eve_trade";
    private static final String usr = "evetrade";
    private static final String pwd = "evetrade";
    
    private Connection conn;
    
    private Database() {
        try {
       
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception ex) {
            System.out.println("error: "+ex);
        }
        
        connect();
    }
    
    public static Database singleton() {
        if(singleton == null) {
            singleton = new Database();
        }
        
        return singleton;
    }
    
    public void clearMarketOrders() throws SQLException {
        Statement stm = createStatement();
        
        stm.execute("TRUNCATE TABLE marketorders");
        
        stm.close();
    }
    
    public Statement createStatement() throws SQLException {
        return conn.createStatement();
    }
    
    public String lookupItemName(long typeId) throws SQLException {
        String foundName = null;
            Statement stm = createStatement();
            
            ResultSet rs = stm.executeQuery("SELECT typeName FROM invtypes WHERE typeID = "+typeId);
            
            rs.first();
            foundName = rs.getString("typeName");
            
            stm.close();
        
        return foundName;
    }
    
    public String lookupStationName(long stationId) throws SQLException {
        String foundName = null;
        Statement stm = createStatement();

        ResultSet rs = stm.executeQuery("SELECT stationName FROM stastations WHERE stationID = "+stationId);

        rs.first();
        foundName = rs.getString("stationName");

        stm.close();
        
        return foundName;
    }

    public String lookupRegionName(long regionId) throws SQLException {
        String foundName = null;
        Statement stm = createStatement();

        ResultSet rs = stm.executeQuery("SELECT regionName FROM mapregions WHERE regionID = "+regionId);

        rs.first();
        foundName = rs.getString("regionName");

        stm.close();
        
        return foundName;
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(url, usr, pwd);
            System.out.println("Connection made!");
        } catch(SQLException e) {
            System.out.println("Error connectiong to database: "+e);
        }
    }

    public PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public double lookupSpace(long itemType) throws SQLException {
        double space = -1;
        Statement stm = createStatement();

        ResultSet rs = stm.executeQuery("SELECT volume, groupID FROM invtypes WHERE typeID = "+itemType);

        rs.first();
        int groupId = rs.getInt("groupID");
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
                space = rs.getDouble("volume");
                break;
        }

        stm.close();
        
        return space;
    }
}
