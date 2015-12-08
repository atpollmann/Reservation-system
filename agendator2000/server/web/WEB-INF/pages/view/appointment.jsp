<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="rounded_corners">
    <h1>Citas</h1>

    <table class="table">
        <thead>
            <tr>
                <td>#</td>
                <td>Lugar</td>

                <td>Fecha</td>
                <td>Hora de inicio</td>
                <td>Hora de término</td>

                <c:if test="${currentUser.isPatient || currentUser.isAdministrator}">
                    <td>Atendido por</td>
                </c:if>
                <c:if test="${currentUser.isProfessional || currentUser.isAdministrator}">
                    <td>Paciente</td>
                </c:if>
                <td>&nbsp;</td>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointments}" var="appointment">
            <tr>
                <td>${appointment.id}</td>
                <td>${appointment.schedule.careSession.location}</td>

                <td>${appointment.schedule.dates.startDate}</td>
                <td>${appointment.schedule.dates.startTime}</td>
                <td>${appointment.schedule.dates.endTime}
                    <sub>(${(appointment.schedule.endDate.time-appointment.schedule.startDate.time)/60000}min)</sub></td>

                <c:if test="${currentUser.isPatient || currentUser.isAdministrator}">
                    <td>${appointment.schedule.professional.firstName} ${appointment.schedule.professional.lastName}</td>
                </c:if>
                <c:if test="${currentUser.isProfessional || currentUser.isAdministrator}">
                    <td>${appointment.patient.firstName} ${appointment.patient.lastName}</td>
                </c:if>

                <td>
                    <c:if test="${appointment.attended}">
                        <a href="#">Expirado</a>
                    </c:if>
                    <c:if test="${!appointment.attended}">
                        <a href="cancelAppointment?id=${appointment.id}">Cancelar</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>