<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="rounded_corners">
<h1>Sesiones de atención</h1>
    <table class="table">
        <thead>
        <tr>
            <td>#</td>
            <td>ONG</td>

            <td>Lugar</td>
            <td>Dirección</td>

            <td>Inicio</td>
            <td>Término</td>

            <td>&nbsp;</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${careSessions}" var="careSession">
            <tr>
                <td>${careSession.id}</td>
                <td>${careSession.ong.name}</td>

                <td>${careSession.location}</td>
                <td>${careSession.address}</td>

                <td>${careSession.dates.startDate}<sub>${careSession.dates.startTime}</sub></td>
                <td>${careSession.dates.endDate}<sub>${careSession.dates.endTime}</sub></td>

                <c:if test="${careSession.valid}">
                    <td><a href="cancelCareSession?id=${careSession.id}">Cancelar</a></td>
                </c:if>
                <c:if test="${!careSession.valid}">
                    <td>&nbsp;</td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>