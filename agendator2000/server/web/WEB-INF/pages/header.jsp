<%@ page import="org.springframework.security.core.userdetails.User" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html; charset=utf-8" %>
<div id="templatemo_header">
	
	<div id="site_title"><h1><a href="../..">FC Barcelona Online</a> <span>Sistema administrativo web</span></h1></div>

	<div id="search_box">
        <span class="welcome">Bienvenido <b><%= ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() %></b>, <a href="../../logout">salir</a></span>
	</div>

	<div class="cleaner"></div>
</div>