<%-- 
    Document   : clearOrders
    Created on : Jul 22, 2012, 2:29:21 PM
    Author     : Rolf
--%>

<%@page import="dk.lystrup.evetradefinder.Database"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Database.singleton().clearMarketOrders();

    response.sendRedirect("index.jsp");

%>