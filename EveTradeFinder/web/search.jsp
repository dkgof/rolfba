<%-- 
    Document   : newjspsearch
    Created on : Jul 22, 2012, 10:24:04 AM
    Author     : Rolf
--%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="dk.lystrup.evetradefinder.Settings"%>
<%@page import="dk.lystrup.evetradefinder.Search"%>
<%@page import="dk.lystrup.evetradefinder.Region"%>
<%@page import="dk.lystrup.evetradefinder.Deal"%>
<%@page import="dk.lystrup.evetradefinder.DealFinder"%>
<%@page import="java.sql.SQLException"%>
<%@page import="dk.lystrup.evetradefinder.Order.OrderType"%>
<%@page import="dk.lystrup.evetradefinder.Order"%>
<%@page import="java.util.LinkedList"%>
<%@page import="dk.lystrup.evetradefinder.Station"%>
<%@page import="java.util.List"%>
<%@page import="dk.lystrup.evetradefinder.SolarSystem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="stylesheet" />
        <link type="text/css" href="css/web.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.21.custom.min.js"></script>
        <title>Trade Finder - Search</title>
    </head>
    <body>
        <h1><a class="home" href="index.jsp">Trade Finder</a></h1>
        
        <a href="settings.jsp">Settings</a><br/>
        
        <%
            long start = System.currentTimeMillis();
        
            String searchType = request.getParameter("searchType");
            
            String from = request.getParameter("fromSystem");
            String to = request.getParameter("toSystem");

            List<Search> recentSearches = (List<Search>) session.getAttribute("recentSearches");
            if(recentSearches == null) {
                recentSearches = new LinkedList<Search>();
            }
            
            recentSearches.add(new Search(from, to, searchType));
            
            session.setAttribute("recentSearches", recentSearches);
            
        %>
          
        <div class="route"><%= from %> -> <%= to %></div>
        <a class="swap" href="search.jsp?fromSystem=<%=to%>&toSystem=<%=from%>&searchType=<%=searchType%>">Swap</a>
        <div class="spacer"></div>
        <div style="font-size: 0.8em;">Show possible scams: <input type="checkbox" onChange="scamChange(this)"></div>
        <br/>
        <script>
            function scamChange(obj) {
                var scamCheck = jQuery(obj);
                if(scamCheck.prop("checked")) {
                    jQuery(".possibleScam").fadeIn(800);
                } else {
                    jQuery(".possibleScam").fadeOut(200);
                }
            }
            
            jQuery(function() {
                jQuery(".possibleScam").hide();
            });
        </script>
        
        <%    
            try {
                Map<Long,List<Order>> fromOrders = new HashMap<Long, List<Order>>();
                Map<Long,List<Order>> toOrders = new HashMap<Long, List<Order>>();

                long beforeMapCreation = System.currentTimeMillis();
                               
                if(searchType.equals("systems")) {
                    SolarSystem fromSystem = SolarSystem.getSystemFromName(from);
                    SolarSystem toSystem = SolarSystem.getSystemFromName(to);

                    fromOrders = fromSystem.getOrders(OrderType.SELL);
                    toOrders = toSystem.getOrders(OrderType.BUY);
                } else if(searchType.equals("regions")) {
                    Region fromRegion = Region.getRegionFromName(from);
                    Region toRegion = Region.getRegionFromName(to);
                    
                    fromOrders = fromRegion.getOrders(OrderType.SELL);
                    toOrders = toRegion.getOrders(OrderType.BUY);
                }

                double mapCreationTime = (System.currentTimeMillis()-beforeMapCreation) / 1000.0;

                List<Deal> deals = DealFinder.findDeals(fromOrders, toOrders);

                double totalProfit = 0;
                                              
                for(Deal deal : deals) {
                    out.println(deal.getFormattedHTML());
                    totalProfit += deal.getAssumedProfit();
                }

                double duration = (System.currentTimeMillis() - start) / 1000.0;
                
                long sellOrders = 0;
                long buyOrders = 0;
                for(List<Order> fromOrderList : fromOrders.values()) {
                    sellOrders += fromOrderList.size();
                }
                for(List<Order> toOrderList : toOrders.values()) {
                    buyOrders += toOrderList.size();
                }
                
                out.println("<p class=\"info\">Searched "+sellOrders+" sell orders and "+buyOrders+" buy orders</p>");
                out.println("<p class=\"info\">Found "+deals.size()+" deals!</p>");
                out.println("<p class=\"info\">Total profit: "+String.format("%.1fM", totalProfit/1000000) +"</p>");
                out.println("<p class=\"info\">Search took: "+String.format("%.2fsec",duration)+" (Map creation: "+String.format("%.2fsec",mapCreationTime)+")</p>");
                
            } catch(SQLException e) {
                out.println("Error looking up orders from systems");
                out.println("From: "+from);
                out.println("To: "+to);
            }
        %>
    </body>
</html>
