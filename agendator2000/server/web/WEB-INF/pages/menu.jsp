<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="cl.usach.ingesoft.agendator.util.RequestHelper" %>
<%!
    String menuMarker(boolean output){
        return output ? "class=\"current\"" : "";
    }
%>

<% int m = RequestHelper.getInt(request, "module"); %>

<div id="templatemo_menu">
    <ul>
        <li><a href="../main/index.html"<%= menuMarker(m==0) %>>Home</a></li>
        <li><a href="../user/index.html"<%= menuMarker(m==7) %>>Usuarios</a></li>
    </ul>
    <div class="cleaner"></div>
</div>
<!-- end of templatemo_menu -->