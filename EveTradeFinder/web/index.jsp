<%-- 
    Document   : index
    Created on : Jul 21, 2012, 3:34:07 PM
    Author     : Rolf
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="dk.lystrup.evetradefinder.Search"%>
<%@page import="java.util.List"%>
<%@page import="dk.lystrup.evetradefinder.Region"%>
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
        <title>Trade Finder - Home</title>
    </head>
    <body>
        <h1>Trade Finder</h1>
        
        <a href="settings.jsp">Settings</a><br/>
        
        <form action="search.jsp" method="post">
            Type: <select id="searchType" name="searchType" onChange="updateSelections(this)">
                <option value="systems">Systems</option>
                <option value="regions">Region</option>
            </select>
            <br />
            From: <input type="text" id="fromSystem" name="fromSystem" />
            <br />
            To: <input type="text" id="toSystem" name="toSystem" />
            <br />
            <br />
            <input type="submit" value="Search">
        </form>
        
        <br />
        Recent searches:<br/>
        <%
            List<Search> recentSearches = (List<Search>) session.getAttribute("recentSearches");
            if(recentSearches == null) {
                recentSearches = new LinkedList<Search>();
            }
        
            int count = 0;
            
            for(int i = recentSearches.size()-1; i>=0; i--) {
                Search s = recentSearches.get(i);
                out.println("<a class=\"recentSearch\" href=\"search.jsp?toSystem="+s.getTo()+"&fromSystem="+s.getFrom()+"&searchType="+s.getType()+"\">"+s.getFrom()+" -> "+s.getTo()+"</a><br />");
                count++;
                if(count == 5) {
                    break;
                }
            }
        %>
        
        <br />
        <br />
        <a href="clearOrders.jsp">Clear all market orders from database</a>

        <script>
            var systemChoices = [
            <%
                boolean first = true;
                for(SolarSystem s : SolarSystem.getAll()) {
                    if(first) {
                        first = false;
                    } else {
                        out.write(",\n");
                    }
                    out.write("\""+s.getName()+"\"");
                }
            %>
            ];
            
            var regionChoices = [
            <%
                first = true;
                for(Region r : Region.getAll()) {
                    if(first) {
                        first = false;
                    } else {
                        out.write(",\n");
                    }
                    out.write("\""+r.getName()+"\"");
                }
            %>
            ];

            function updateSelections(obj) {
                var jObj = jQuery(obj);
                if(jObj.val() == "regions") {
                    jQuery("#fromSystem").autocomplete({
                        source: regionChoices
                    });

                    jQuery("#toSystem").autocomplete({
                        source: regionChoices
                    });
                } else if(jObj.val() == "systems") {
                    jQuery("#fromSystem").autocomplete({
                        source: systemChoices
                    });

                    jQuery("#toSystem").autocomplete({
                        source: systemChoices
                    });
                }
            }
            
            updateSelections(document.getElementById("searchType"));
        </script>
    </body>
</html>
