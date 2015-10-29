<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.springframework.security.core.userdetails.User" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<div class="col_w280 float_r">
	<h2>Usuario autenticado</h2>
	
	<div class="lbe_box">
		<h3>Bienvenido <b><%= ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() %></b>.</h3>
	    <p>Morbi pellentesque, libero vitae fermentum tincidunt libero accumsan erat, sit amet ornare...</p>

        <p class="date">&Uacute;ltimo acceso el <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            out.print(sdf.format(new Date()));
        %></p>
	</div>

	<div class="cleaner h30"></div>
	
	<h3>Our Latest Project</h3>
	
	<div class="sb_lp_box">
		<img src="../../images/gallery/image_04_s.jpg" alt="image 5" />
		<p>Duis convallis odio et velit venenatis ac venenatis turpis mattis varius quam et laoreet. <a href="#">More...</a></p>
	</div>
	
	<div class="sb_lp_box">
		<img src="../../images/gallery/image_02_s.jpg" alt="image 6" />
		<p>Duis convallis odio et velit venenatis ac venenatis turpis mattis varius quam et laoreet. <a href="#">More...</a></p>
	</div>
	
	<div class="sb_lp_box">
		<img src="../../images/gallery/image_03_s.jpg" alt="image 7" />
		<p>Duis convallis odio et velit venenatis ac venenatis turpis mattis varius quam et laoreet. <a href="#">More...</a></p>
	</div>
	
</div>