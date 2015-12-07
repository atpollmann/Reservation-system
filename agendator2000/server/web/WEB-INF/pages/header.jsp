<%@page contentType="text/html;charset=UTF-8"%>

<%@ page import="org.springframework.security.core.userdetails.User" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<span class="welcome">Bienvenido <b><%= ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() %></b>, <a href="../../logout">salir</a></span>
