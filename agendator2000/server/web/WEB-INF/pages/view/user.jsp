<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="rounded_corners">
    <h1>Usuarios</h1>

    <table class="table">
        <thead>
        <tr>
            <td>#</td>
            <td>Tipo</td>

            <td>Nombre</td>
            <td>Apellido</td>
            <td>Email</td>
            <td>RUN</td>

            <td>&nbsp;</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.roleName}</td>

                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.run}</td>

                <c:if test="${currentUser.id != user.id}">
                <td><a href="deleteUser?id=${user.id}">Eliminar</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>