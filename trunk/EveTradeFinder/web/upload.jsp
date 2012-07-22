<%-- 
    Document   : upload
    Created on : Jul 21, 2012, 4:10:02 PM
    Author     : Rolf
--%>

<%@page import="dk.lystrup.evetradefinder.Order"%>
<%
    String dataString = request.getParameter("data");

    String[] dataSplit = dataString.split("\n");

    String[] dataColumns = dataSplit[0].split(",");

    int priceColumn = -1;
    int volRemainingColumn = -1;
    int stationIDColumn = -1;
    int typeIDColumn = -1;
    int bidID = -1;
    int orderIDColumn = -1;
    int minVolColumn = -1;
    
    int i = 0;
    for(String column : dataColumns) {
        if(column.equals("price")) {
            priceColumn = i;
        } else if(column.equals("volRemaining")) {
            volRemainingColumn = i;
        } else if(column.equals("typeID")) {
            typeIDColumn = i;
        } else if(column.equals("stationID")) {
            stationIDColumn = i;
        } else if(column.equals("bid")) {
            bidID = i;
        } else if(column.equals("orderID")) {
            orderIDColumn = i;
        } else if(column.equals("minVolume")) {
            minVolColumn = i;
        }
        
        i++;
    }
    
    for(i = 1; i<dataSplit.length; i++) {
        String[] dataValues = dataSplit[i].split(",");
        
        double price = Double.parseDouble(dataValues[priceColumn]);
        long typeID = Long.parseLong(dataValues[typeIDColumn]);
        long stationID = Long.parseLong(dataValues[stationIDColumn]);
        double volume = Double.parseDouble(dataValues[volRemainingColumn]);
        boolean bid = Boolean.parseBoolean(dataValues[bidID]);
        long orderID = Long.parseLong(dataValues[orderIDColumn]);
        double minVolume = Double.parseDouble(dataValues[minVolColumn]);
        
        Order order = new Order(orderID, typeID, volume, minVolume, price, stationID, bid);
        order.save();
    }
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
Complete!