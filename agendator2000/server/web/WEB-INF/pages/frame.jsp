<%@ page import="java.net.URLDecoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Agendator</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 20px;
            padding-bottom: 60px;
        }

        /* Custom container */
        .container {
            margin: 0 auto;
            max-width: 1000px;
        }
        .container > hr {
            margin: 60px 0;
        }

        /* Main marketing message and sign up button */
        .jumbotron {
            margin: 80px 0;
            text-align: center;
        }
        .jumbotron h1 {
            font-size: 100px;
            line-height: 1;
        }
        .jumbotron .lead {
            font-size: 24px;
            line-height: 1.25;
        }
        .jumbotron .btn {
            font-size: 21px;
            padding: 14px 24px;
        }

        /* Supporting marketing content */
        .marketing {
            margin: 60px 0;
        }
        .marketing p + h4 {
            margin-top: 28px;
        }


        /* Customize the navbar links to be fill the entire space of the .navbar */
        .navbar .navbar-inner {
            padding: 0;
        }
        .navbar .nav {
            margin: 0;
            display: table;
            width: 100%;
        }
        .navbar .nav li {
            display: table-cell;
            width: 1%;
            float: none;
        }
        .navbar .nav li a {
            font-weight: bold;
            text-align: center;
            border-left: 1px solid rgba(255,255,255,.75);
            border-right: 1px solid rgba(0,0,0,.1);
        }
        .navbar .nav li:first-child a {
            border-left: 0;
            border-radius: 3px 0 0 3px;
        }
        .navbar .nav li:last-child a {
            border-right: 0;
            border-radius: 0 3px 3px 0;
        }
    </style>
    <link href="../../css/app.css" rel="stylesheet">
    <link href="../../css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="../../css/fullcalendar.css" rel="stylesheet">
    <link href="../../css/cupertino/jquery-ui.min.css" rel="stylesheet">

    <script src="../../js/jquery-1.9.1.js"></script>
    <script src="../../js/jquery-ui-1.10.3.custom.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/app.js"></script>
    <script src="../../js/moment.min.js"></script>
    <script src="../../js/fullcalendar.min.js"></script>
</head>

<body>

<div class="container">

    <jsp:include page="menu.jsp"/>

    <c:if test="${not empty errorStr}">
        <script type="text/javascript">
            $(function() {
                var errorDelay = 5000;
                setTimeout(function () {
                    $('#frame_error_container').fadeOut();
                }, errorDelay);
            });
        </script>
        <div id="frame_error_container" class="alert alert-danger" role="alert">
            <strong>Error!</strong> <%= URLDecoder.decode(String.valueOf(request.getAttribute("errorStr")), "UTF-8") %>
        </div>
    </c:if>

    <c:if test="${not empty okStr}">
        <script type="text/javascript">
            $(function() {
                var okDelay = 5000;
                setTimeout(function () {
                    $('#frame_ok_container').fadeOut();
                }, okDelay);
            });
        </script>
        <div id="frame_ok_container" class="alert alert-success" role="alert">
            <strong>Ok!</strong> <%= URLDecoder.decode(String.valueOf(request.getAttribute("okStr")), "UTF-8") %>
        </div>
    </c:if>

    <jsp:include page="${contentName}.jsp"/>

    <hr>

    <jsp:include page="footer.jsp"/>

</div> <!-- /container -->

</body>
</html>
