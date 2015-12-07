<%@page contentType="text/html;charset=UTF-8"%>

<div class="masthead">
    <h3 class="muted"><a href="../..">Agendator</a></h3>

    <jsp:include page="header.jsp"/>

    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <ul class="nav">
                    <li class="${menuName == 'calendar' ? 'active' : ''}"><a href="../calendar/index.html">Calendario</a></li>
                    <li class="${menuName == 'appointment' ? 'active' : ''}"><a href="../appointment/index.html">Citas [for patient and professional]</a></li>
                    <li class="${menuName == 'profile' ? 'active' : ''}"><a href="../profile/index.html">Perfil</a></li>
                    <li class="${menuName == 'contact' ? 'active' : ''}"><a href="../contact/index.html">Contacto</a></li>
                    <li class="${menuName == 'professional' ? 'active' : ''}"><a href="../professional/index.html">[Disponibilidad for professional]</a></li>
                    <li class="${menuName == 'user' ? 'active' : ''}"><a href="../user/index.html">[Usuario for admin]</a></li>
                    <li class="${menuName == 'careSession' ? 'active' : ''}"><a href="../careSession/index.html">[Sesión for admin]</a></li>
                </ul>
            </div>
        </div>
    </div><!-- /.navbar -->
</div>