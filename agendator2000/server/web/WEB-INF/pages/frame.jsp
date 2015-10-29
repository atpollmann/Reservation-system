<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="cl.cc5604.fcbarcelonaonline.util.RequestHelper" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>FC Barcelona Online</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="fc barcelona online, fc, barcelona, online, equipo, futbol"/>
    <meta name="description" content="Sistema administrativo web de FC Barcelona"/>

    <link rel="stylesheet" type="text/css" href="../../css/templatemo_style.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/redmond/jquery-ui-1.10.3.custom.min.css"/>

    <script type="text/javascript" src="../../js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="../../js/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="../../js/scripts.js"></script>
    <script type="text/javascript" src="../../js/jquery.ui.datepicker-es.js"></script>

    <link rel="stylesheet" type="text/css" href="../../css/styles.css" media="screen"/>
    <script type="text/javascript">
        $(function(){
            $('.button').button();
        });
    </script>

</head>
<body class="subpage">

<div id="templatemo_wrapper">

    <jsp:include page="header.jsp"/>

    <jsp:include page="menu.jsp"/>

    <jsp:include page="middle_subpage.jsp"/>

    <%
        int m = RequestHelper.getInt(request, "module");
        switch (m) {
            case -1: %><jsp:include page="error.jsp"/><% break;
            case 0: %><jsp:include page="main.jsp"/><% break;
            case 1: %><jsp:include page="active.jsp"/><% break;
            case 2: %><jsp:include page="associate.jsp"/><% break;
            case 8: %><jsp:include page="contact.jsp"/><% break;
            case 3: %><jsp:include page="contract.jsp"/><% break;
            case 4: %><jsp:include page="finances.jsp"/><% break;
            case 5: %><jsp:include page="passive.jsp"/><% break;
            case 6: %><jsp:include page="staff.jsp"/><% break;
            case 7: %><jsp:include page="user.jsp"/><% break;
        }
    %>


</div>
<!-- end of wrapper -->

<jsp:include page="footer.jsp"/>

</body>
</html>