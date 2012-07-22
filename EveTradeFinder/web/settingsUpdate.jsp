<%-- 
    Document   : settingsUpdate
    Created on : Jul 22, 2012, 1:59:11 PM
    Author     : Rolf
--%>

<%@page import="dk.lystrup.evetradefinder.Settings"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    double profit = Double.parseDouble(request.getParameter("profit").replaceAll(",", "."));
    double volume = Double.parseDouble(request.getParameter("volume").replaceAll(",", "."));
    double maxCost = Double.parseDouble(request.getParameter("maxCost").replaceAll(",", "."));
    double brokerFee = Double.parseDouble(request.getParameter("brokerFee").replaceAll(",", "."));
    double stationTax = Double.parseDouble(request.getParameter("stationTax").replaceAll(",", "."));
    
    Settings setting = Settings.singleton();
    
    setting.setBrokerFee(brokerFee);
    setting.setMaxCost(maxCost);
    setting.setMaxVolume(volume);
    setting.setMinProfit(profit);
    setting.setStationTax(stationTax);

    response.sendRedirect("index.jsp");

%>