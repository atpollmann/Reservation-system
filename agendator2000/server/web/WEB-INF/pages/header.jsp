<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="cl.usach.ingesoft.agendator.util.RequestHelper" %>

<span class="welcome">
    Bienvenido
    <span class="highlight"><%= RequestHelper.getUserEmail() %></span>,
    <a href="../../logout">salir</a></span>
