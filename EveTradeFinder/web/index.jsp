<%-- 
    Document   : index
    Created on : Jul 21, 2012, 3:34:07 PM
    Author     : Rolf
--%>

<%@page import="dk.lystrup.evetradefinder.SolarSystem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.21.custom.min.js"></script>
        <title>Trade Finder - Home</title>
    </head>
    <body>
        <h1>Trade Finder</h1>
        
        <form action="search.jsp" method="post">
            From: <input type="text" id="fromSystem" name="fromSystem" />
            <br />
            To: <input type="text" id="toSystem" name="toSystem" />
            <br />
            <br />
            <input type="submit" value="Search">
        </form>
        
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
            
            jQuery("#fromSystem").autocomplete({
                source: systemChoices
            });

            jQuery("#toSystem").autocomplete({
                source: systemChoices
            });
        </script>
    </body>
</html>
