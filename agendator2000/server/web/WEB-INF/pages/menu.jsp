<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="cl.cc5604.fcbarcelonaonline.util.RequestHelper" %>
<%!
    String menuMarker(boolean output){
        return output ? "class=\"current\"" : "";
    }
%>

<% int m = RequestHelper.getInt(request, "module"); %>

<div id="templatemo_menu">
    <ul>
        <li><a href="../main/index.html"<%= menuMarker(m==0) %>>Home</a></li>
        <li><a href="../active/index.html"<%= menuMarker(m==1) %>>Activos</a></li>
        <li><a href="../associate/index.html"<%= menuMarker(m==2 || m==8) %>>Socios</a></li>
        <li><a href="../contract/index.html"<%= menuMarker(m==3) %>>Contratos</a></li>
        <li><a href="../finance/index.html"<%= menuMarker(m==4) %>>Finanzas</a></li>
        <li><a href="../passive/index.html"<%= menuMarker(m==5) %>>Pasivos</a></li>
        <li><a href="../staff/index.html"<%= menuMarker(m==6) %>>Personal</a></li>
        <li><a href="../user/index.html"<%= menuMarker(m==7) %>>Usuarios</a></li>
    </ul>
    <div class="cleaner"></div>
</div>
<!-- end of templatemo_menu -->