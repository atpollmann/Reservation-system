<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="templatemo_main">

    <script type="text/javascript">

        /* funcion que agrega la siguiente funcionalidad:
            Al input le agrega un listener que al soltar una tecla,
            se consulta si el valor escrito (nombre de usuario) esta disponible o no.
            El resultado de la consulta lo coloca como texto en el elemento span.
            Si si especifica una funcion, se ejecuta al obtener la respuesta de la consulta.
         */
        function add_availableUsername_check(input, span, fxLastUsername){
            $(input).keyup(function(){
                var usrStr = $.trim($(this).val());
                if(usrStr && usrStr!=fxLastUsername()){
                    $.post('availableUsername', {username:usrStr}, function(data, textStatus, jqXHR){
                        showError();
                        if(data == 'yes'){
                            showMsg(span, 'Usuario disponible.', input);
                        }else{
                            showError(span, 'Usuario no disponible.', input);
                        }
                    });
                }else{
                    showError(span, '');
                }
            });
        }

        $(function () {
            $("#edit-div").dialog({
                autoOpen:false,
                height:230,
                width:340,
                modal:true
            });

            add_availableUsername_check('#username', '#newUser-msg', function(){return null;});
            add_availableUsername_check('#edit_username', '#oldUser-msg', function(){return window.lastUsername;});

            $('#sendNewUser').click(function (event) {
                var username = $.trim($('#username').val());
                var password = $.trim($('#password').val());
                var passwordConfirm = $.trim($('#passwordConfirm').val());

                // limpiar errores del submit anterior
                showError('#newUser-msg', '', '#username,#password,#passwordConfirm');

                if (!username) {
                    showError('#newUser-msg', 'Ingrese un nombre de usuario.', '#username');
                    return false;
                }else{
                    showError('#newUser-msg', '', '#username');
                }

                if (!password) {
                    showError('#newUser-msg', 'Ingrese una contraseña.', '#password');
                    return false;
                }else{
                    showError('#newUser-msg', '', '#password');
                }

                if (!passwordConfirm) {
                    showError('#newUser-msg', 'Ingrese la confirmación de contraseña.', '#passwordConfirm');
                    return false;
                }else{
                    showError('#newUser-msg', '', '#passwordConfirm');
                }

                if (passwordConfirm != password) {
                    showError('#newUser-msg', 'Las contraseñas no coinciden.', '#password,#passwordConfirm');
                    return false;
                }else{
                    showError('#newUser-msg', '', '#password,#passwordConfirm');
                }

                // volver a colocar valores, para sanitizar (algo) lo que ha ingresado el usuario
                $('#username').val(username);
                $('#password').val(password);
                $('#passwordConfirm').val(passwordConfirm);

                // consultar si el usuario esta disponible

                // enviar el form solo si el usuario esta disponible

                $.post('availableUsername', {username:username}, function(data, textStatus, jqXHR){
                    if(data == 'yes'){
                        showError('#newUser-msg', '', '#username');
                        $('#newUserForm').submit();
                    }else{
                        showError('#newUser-msg', 'Usuario no disponible.', '#username');
                    }
                });


                return false;
            });

            $('#sendOldUser').click(function () {
                var username = $.trim($('#edit_username').val());
                var password = $.trim($('#edit_password').val());
                var passwordConfirm = $.trim($('#edit_passwordConfirm').val());

                // limpiar errores del submit anterior
                showError('#oldUser-msg', '', '#edit_username,#edit_password,#edit_passwordConfirm');

                if (!username) {
                    showError('#oldUser-msg', 'Ingrese un nombre de usuario.', '#edit_username');
                    return false;
                }else{
                    showError('#oldUser-msg', '', '#edit_username');
                }

                if (!password) {
                    showError('#oldUser-msg', 'Ingrese una contraseña.', '#edit_password');
                    return false;
                }else{
                    showError('#oldUser-msg', '', '#edit_password');
                }

                if (!passwordConfirm) {
                    showError('#oldUser-msg', 'Ingrese la confirmación de contraseña.', '#edit_passwordConfirm');
                    return false;
                }else{
                    showError('#oldUser-msg', '', '#edit_passwordConfirm');
                }

                if (passwordConfirm != password) {
                    showError('#oldUser-msg', 'Las contraseñas no coinciden.', '#edit_password,#edit_passwordConfirm');
                    return false;
                }else{
                    showError('#oldUser-msg', '', '#edit_password,#edit_passwordConfirm');
                }

                $('#edit_username').val(username);
                $('#edit_password').val(password);
                $('#edit_passwordConfirm').val(passwordConfirm);

                // consultar si el usuario esta disponible

                if(window.lastUsername==username){
                    showError('#oldUser-msg', '', '#edit_username');
                    $('#editForm').submit();
                }else{
                    // enviar el form solo si el usuario esta disponible
                    $.post('availableUsername', {username:username}, function(data, textStatus, jqXHR){
                        if(data == 'yes'){
                            showError('#oldUser-msg', '', '#edit_username');
                            $('#editForm').submit();
                        }else{
                            showError('#oldUser-msg', 'Usuario no disponible.', '#edit_username');
                        }
                    });
                }
                return false;
            });
        });

        function deleteUser(a, id) {
            if(window.confirm("Realmente desea borrar el usuario con id #"+id+"?")){
                $.post('handleDelete', {id:id}, function (data, textStatus, jqXHR) {
                    if (data == 'ok') {
                        $(a.parentNode.parentNode).remove();
                        var counter = parseInt($('#counter').text());
                        $('#counter').text("" + (counter - 1));
                    } else {
                        window.alert('Error al borrar.');
                    }
                });
            }
            return false;
        }
        window.lastUsername = null;
        function editUser(id, username, password) {
            $('#edit_idUser').val("" + id);
            $('#edit_username').val(username);
            $('#edit_password').val(password);
            $('#edit_passwordConfirm').val(password);
            window.lastUsername = username;

            $("#edit-div").dialog('option', 'title', 'Editar usuario #'+id);
            $("#edit-div").dialog("open");

            return false;
        }

    </script>

    <div class="col_w900 col_w900_last">

        <p>En esta sección se pueden manejar los usuarios de este sistema administrativo web.</p>

        <div class="col_w50 float_l">

            <div class="post_box">

                <h2>Usuarios ingresados en el sistema</h2>

                Hay <b><span id="counter">${fn:length(availableUser)}</span></b> usuario(s).

                <display:table name="availableUser" id="userTable" class="displayTable">
                    <display:column property="id" title="Id" class="id_col"/>
                    <display:column property="run" title="Run"/>
                    <display:column property="firstName" title="FirstName"/>
                    <display:column property="lastName" title="LastName"/>
                    <display:column property="type" title="type"/>

                </display:table>

                <br/>
                <br/>

                <small><b>Nota:</b> en la tabla se muestran las contraseñas, solo porque esta aplicación es para fines didácticos.
                    En producción se debe implementar un mecanismo de encriptación u ocultamiento de estos campos, así como
                usar un canal seguro https.</small>

                <div class="cleaner"></div>
            </div>

        </div>

        <div class="col_w50 float_r">
            <div class="post_box">

                <h2>Agregar nuevo usuario</h2>

                <span id="newUser-msg">&nbsp;</span>

                <form id="newUserForm" action="handlePost" method="post">

                    <input type="hidden" id="idUser" name="id" value=""/>

                    <table>
                        <tr>
                            <td class="centered"><label for="username">Usuario</label></td>
                            <td>:</td>
                            <td><input id="username" name="username" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="centered"><label for="password">Contraseña</label></td>
                            <td>:</td>
                            <td><input id="password" name="password" type="password" value=""/></td>
                        </tr>
                        <tr>
                            <td class="centered"><label for="passwordConfirm">Confirmar contraseña</label></td>
                            <td>:</td>
                            <td><input id="passwordConfirm" name="passwordConfirm" type="password" value=""/></td>
                        </tr>
                        <tr>
                            <td><input id="sendNewUser" class="button" type="button" value="enviar"/></td>
                            <td>&nbsp;</td>
                            <td><input class="button" type="button" value="limpiar"
                                       onclick="$('#username,#password,#passwordConfirm').val(''); return false;"/></td>
                        </tr>
                    </table>

                </form>

                <!-- formulario de edit -->

                <div id="edit-div" title="Editar usuario">

                    <span id="oldUser-msg">&nbsp;</span>

                    <form id="editForm" action="handlePost" method="post">

                        <input type="hidden" id="edit_idUser" name="id" value=""/>

                        <table>
                            <tr>
                                <td align="center"><label for="edit_username">Usuario</label></td>
                                <td>:</td>
                                <td><input id="edit_username" name="username" type="text" value=""/></td>
                            </tr>
                            <tr>
                                <td align="center"><label for="edit_password">Contraseña</label></td>
                                <td>:</td>
                                <td><input id="edit_password" name="password" type="password" value=""/></td>
                            </tr>
                            <tr>
                                <td align="center"><label for="edit_passwordConfirm">Confirmar contraseña</label></td>
                                <td>:</td>
                                <td><input id="edit_passwordConfirm" name="passwordConfirm" type="password" value=""/>
                                </td>
                            </tr>
                            <tr>
                                <td><input id="sendOldUser" class="button" type="submit" value="enviar"/></td>
                                <td>&nbsp;</td>
                                <td><input class="button" type="button" value="cancelar"
                                           onclick="$('#edit-div').dialog('close'); return false;"/></td>
                            </tr>
                        </table>
                    </form>
                </div>

                <div class="cleaner"></div>
            </div>


        </div>

        <div class="cleaner"></div>
    </div>

    <div class="cleaner"></div>
</div>
<!-- end of main -->
