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

        ResultSet rs = stm.executeQuery("SELECT volume FROM invtypes WHERE typeID = "+itemType);

        rs.first();
        space = rs.getDouble("volume");

        stm.close();
        
        return space;
    }
}
