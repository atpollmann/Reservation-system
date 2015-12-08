<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<style type="text/css">
    .profile_form {
        width: 400px;
        margin: auto;
    }

    .profile_form td:first-child {
        text-align: right;
        padding-right: 10px;
    }
</style>

<div class="rounded_corners">
    <h1>Perfil</h1>
    <span class="wtodo">validar usando jquery</span>
    <form role="form" class="profile_form" method="post" action="updateProfile">
        <table>
            <tr>
                <td>
                    <label for="type">Tipo de usuario:</label>
                </td>
                <td>
                    <input type="text" class="form-control" id="type" value="${currentUser.roleName}" readonly="readonly">
                </td>
            </tr>

            <tr>
                <td>
                    <label for="firstName">Nombre:</label>
                </td>
                <td>
                    <input name="firstName" type="text" class="form-control" id="firstName" value="${currentUser.firstName}">
                </td>
            </tr>

            <tr>
                <td>
                    <label for="lastName">Apellido:</label>
                </td>
                <td>
                    <input name="lastName" type="text" class="form-control" id="lastName" value="${currentUser.lastName}">
                </td>
            </tr>

            <tr>
                <td>
                    <label for="email">Email:</label>
                </td>
                <td>
                    <input name="email" type="text" class="form-control" id="email" value="${currentUser.email}" readonly="readonly">
                </td>
            </tr>

            <tr>
                <td>
                    <label for="run">RUN:</label>
                </td>
                <td>
                    <input name="run" type="text" class="form-control" id="run" value="${currentUser.run}-${currentUser.dv}">
                </td>
            </tr>

            <tr>
                <td>
                    <label for="currentPassword">Contraseña Actual:</label>
                </td>
                <td>
                    <input name="currentPassword" type="text" class="form-control" id="currentPassword" value="">
                </td>
            </tr>

            <tr>
                <td>
                    <label for="newPassword">Contraseña Nueva:</label>
                </td>
                <td>
                    <input name="newPassword" type="text" class="form-control" id="newPassword" value="">
                </td>
            </tr>

            <tr>
                <td>
                    <label for="confirmPassword">Confirmar contraseña:</label>
                </td>
                <td>
                    <input name="confirmPassword" type="text" class="form-control" id="confirmPassword" value="">
                </td>
            </tr>


        </table>

        <button type="submit" class="btn btn-default">Enviar</button>
    </form>

</div>