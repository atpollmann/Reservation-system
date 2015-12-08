<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<span class="welcome">
    Bienvenido
    <span class="highlight">${currentUser.firstName} ${currentUser.lastName}</span><span class="wtodo">${currentUser.id} - ${currentUser.roleName}</span>,
    <a href="../../logout">salir</a></span>
