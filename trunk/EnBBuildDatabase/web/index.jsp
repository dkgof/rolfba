<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="WEB-INF/jspf/header.jspf" %>

<h1>EnB Buildlist</h1>

<input type="checkbox" id="test" /><label for="test">Toggle</label>

<script>
    $(function() {
        $("#test").button();
    });
</script>

<%@include file="WEB-INF/jspf/footer.jspf" %>
