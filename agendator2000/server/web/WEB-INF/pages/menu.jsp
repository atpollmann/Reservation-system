<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="masthead">
    <h3 class="muted"><a href="../..">Agendator</a></h3>

    <jsp:include page="header.jsp"/>

    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <ul class="nav">
                    <c:if test="${currentUser.isAdministrator}">
                        <li class="${menuName == 'careSession' ? 'active' : ''}"><a href="../careSession/index.html">[Sesiones]</a></li>
                        <li class="${menuName == 'user' ? 'active' : ''}"><a href="../user/index.html">[Usuarios]</a></li>
                    </c:if>
                    <c:if test="${currentUser.isProfessional||currentUser.isPatient}">
                        <li class="${menuName == 'calendar' ? 'active' : ''}"><a href="../calendar/index.html">[Calendario]</a></li>
                    </c:if>
                    <li class="${menuName == 'appointment' ? 'active' : ''}"><a href="../appointment/index.html">Citas</a></li>
                    <li class="${menuName == 'profile' ? 'active' : ''}"><a href="../profile/index.html">Perfil</a></li>
                    <c:if test="${currentUser.isProfessional}">
                        <li class="${menuName == 'professional' ? 'active' : ''}"><a href="../professional/index.html">[Disponibilidad]</a></li>
                    </c:if>
                    <c:if test="${currentUser.isPatient}">
                        <li class="${menuName == 'contact' ? 'active' : ''}"><a href="../contact/index.html">Contacto</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div><!-- /.navbar -->
</div>