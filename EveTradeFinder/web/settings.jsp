<%-- 
    Document   : newjspsettings
    Created on : Jul 22, 2012, 1:52:08 PM
    Author     : Rolf
--%>

<%@page import="dk.lystrup.evetradefinder.Settings"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="stylesheet" />
        <link type="text/css" href="css/web.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.21.custom.min.js"></script>
        <title>Trade Finder - Settings</title>
    </head>
    <body>
        <h1><a class="home" href="index.jsp">Trade Finder</a></h1>
        
        <form action="settingsUpdate.jsp" method="post">
            Min profit: <input type="text" name="profit" value="<%=String.format("%.1f",Settings.singleton().getMinProfit())%>"><br/>
            Max volume: <input type="text" name="volume" value="<%=String.format("%.1f",Settings.singleton().getMaxVolume())%>"><br/>
            Max investment: <input type="text" name="maxCost" value="<%=String.format("%.1f",Settings.singleton().getMaxCost())%>"><br/>
            Broker fee: <input type="text" name="brokerFee" value="<%=String.format("%.4f",Settings.singleton().getBrokerFee())%>"><br/>
            Station tax: <input type="text" name="stationTax" value="<%=String.format("%.4f",Settings.singleton().getStationTax())%>"><br/>
            <input type="submit" value="Save settings">
        </form>
    </body>
</html>
