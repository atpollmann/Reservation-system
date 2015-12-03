<div class="masthead">
    <h3 class="muted"><a href="../..">Agendator</a></h3>

    <jsp:include page="header.jsp"/>

    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <ul class="nav">
                    <li class="${menuName == 'my_calendar' ? 'active' : ''}"><a href="../my_calendar/index.html">Mi Calendario</a></li>
                    <li class="${menuName == 'my_appointments' ? 'active' : ''}"><a href="../my_appointments/index.html">Mis Horas</a></li>
                    <li class="${menuName == 'my_attentions' ? 'active' : ''}"><a href="../my_attentions/index.html">Mis Atenciones</a></li>
                    <li class="${menuName == 'my_profile' ? 'active' : ''}"><a href="../my_profile/index.html">Mis Datos</a></li>
                    <li class="${menuName == 'users' ? 'active' : ''}"><a href="../users/index.html">[Usuarios]</a></li>
                    <li class="${menuName == 'contact' ? 'active' : ''}"><a href="../contact/index.html">Contacto</a></li>
                </ul>
            </div>
        </div>
    </div><!-- /.navbar -->
</div>