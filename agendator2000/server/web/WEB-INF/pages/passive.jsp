<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="templatemo_main">

<script type="text/javascript">

    $(function () {
        $("#edit-div").dialog({
            autoOpen:false,
            height:180,
            width:340,
            modal:true
        });

        $('#sendnewPassive').click(function (event) {
            var value = $.trim($('#value').val());
            var idStatus = $.trim($('#status').val());

            // limpiar errores del submit anterior
            showError('#newPassive-msg', '', '#value,#status');

            if (!value) {
                showError('#newPassive-msg', 'Ingrese un Valor.', '#value');
                return false;
            } else {
                showError('#newPassive-msg', '', '#value');
            }

            if(!isNumeric(value)){
                showError('#newPassive-msg', 'Debe ingresar un numero', '#edit_value');
                return false;
            } else {
                showError('#newPassive-msg', '', '#edit_value');
            }

            if (!idStatus) {
                showError('#newPassive-msg', 'Debe seleccionar un estado.', '#status');
                return false;
            } else {
                showError('#newPassive-msg', '', '#status');
            }

            // volver a colocar valores, para sanitizar (algo) lo que ha ingresado el usuario
            $('#value').val(value);
            $('#status').val(idStatus);

            $('#newPassiveForm').submit();

            return false;
        });

        $('#sendOldPassive').click(function () {
            var value = $.trim($('#edit_value').val());
            var idStatus = $.trim($('#edit_status').val());

            // limpiar errores del submit anterior
            showError('#oldPassive-msg', '', '#edit_value,#edit_status');

            if (!value) {
                showError('#oldPassive-msg', 'Ingrese un Valor', '#edit_value');
                return false;
            } else {

            }

            if(!isNumeric(value)){
                showError('#oldPassive-msg', 'Debe ingresar un numero', '#edit_value');
                return false;
            } else {
                showError('#oldPassive-msg', '', '#edit_value');
            }

            if (!idStatus) {
                showError('#oldPassive-msg', 'Debe seleccionar un estado.', '#edit_status');
                return false;
            } else {
                showError('#oldPassive-msg', '', '#edit_status');
            }

            $('#edit_value').val(value);
            $('#edit_status').val(idStatus);

            $('#editForm').submit();

            return false;
        });
    });

    function deletePassive(a, id) {
        if(window.confirm('Realmente desea borrar el pasivo con id #'+id+'?')){
            $.post('handleDelete', {id:id}, function (data, textStatus, jqXHR) {
                if (data == 'ok') {
                    $(a.parentNode.parentNode).remove();
                    counter = parseInt($('#counter').text());
                    $('#counter').text("" + (counter - 1));
                } else {
                    window.alert('Error al borrar.');
                }
            });
        }
        return false;
    }

    function editPassive(id, username, idstatus) {
        $('#edit_idPassive').val("" + id);
        $('#edit_value').val(username);
        $('#edit_status').val(idstatus);
        $("#edit-div").dialog('option', 'title', 'Editar pasivo #'+id);
        $("#edit-div").dialog("open");
        return false;
    }

</script>


<div class="col_w900 col_w900_last">

    <p>
        En esta sección se pueden manejar los pasivos del club. Aquí debe especificarse si están pendientes o pagados.
    </p>

    <div class="col_w50 float_l">

        <div class="post_box" id="passiveDiv">
            <h2>Pasivos ingresados en el sistema</h2>

            Hay <b><span id="counter">${fn:length(availablePassive)}</span></b> pasivo(s).

            <display:table name="availablePassive" id="passiveTable" class="displayTable">
                <display:column property="id" title="ID" class="id_col"/>
                <display:column property="formattedValue" title="Valor (CLP)"/>
                <display:column property="passiveStatus.status" title="Estado"/>
                <display:column title="Editar" class="edit_col">
                    <a href="#"
                       onclick="return editPassive(${passiveTable.id},'${passiveTable.value}','${passiveTable.passiveStatus.id}');">
                        <img src="../../images/edit.png" class="edit-img" alt="Editar" title="Editar" width="16"
                             height="16"/>
                    </a>
                </display:column>
                <display:column title="Borrar" class="delete_col">
                    <a href="#" onclick="return deletePassive(this, ${passiveTable.id});">
                        <img src="../../images/delete.png" class="delete-img" alt="Borrar" title="Borrar"/>
                    </a>
                </display:column>
            </display:table>

        </div>

    </div>

    <div class="col_w50 float_r">

        <div class="post_box">

            <h2>Agregar nuevo pasivo</h2>

            <span id="newPassive-msg">&nbsp;</span>

            <form id="newPassiveForm" action="handlePost" method="post">

                <input type="hidden" id="idPassive" name="id" value=""/>

                <table>
                    <tr>
                        <td class="centered"><label for="value">Valor (CLP)</label></td>
                        <td>:</td>
                        <td><input id="value" name="value" type="text" value=""/></td>
                    </tr>
                    <tr id="status-row">
                        <td class="centered"><label for="status">Estado Pasivo</label></td>
                        <td>:</td>
                        <td>
                            <select name="status" id="status" style="width: 100%;">
                                <c:forEach var="status" items="${availablePassiveStatus}">
                                    <option value="${status.id}">${status.status}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><input id="sendnewPassive" class="button" type="button" value="enviar"/></td>
                        <td>&nbsp;</td>
                        <td><input class="button" type="button" value="limpiar"
                                   onclick="$('#value,#status').val(''); return false;"/></td>
                    </tr>
                </table>

            </form>

            <!-- formulario de edit -->

            <div id="edit-div" title="Editar pasivo">

                <span id="oldPassive-msg">&nbsp;</span>

                <form id="editForm" action="handlePost" method="post">

                    <input type="hidden" id="edit_idPassive" name="id" value="<c:out value='${id}'/>"/>

                    <table>
                        <tr>
                            <td align="center"><label for="edit_value">Valor (CLP)</label></td>
                            <td>:</td>
                            <td><input id="edit_value" name="value" type="text" value=""/></td>
                        </tr>

                        <tr>
                            <td class="centered"><label for="edit_status">Tipo Pasivo</label></td>
                            <td>:</td>
                            <td>
                                <select name="status" id="edit_status" style="width: 100%;">
                                    <c:forEach var="status" items="${availablePassiveStatus}">
                                        <option value="${status.id}">${status.status}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <td><input id="sendOldPassive" class="button" type="submit" value="enviar"/></td>
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
</div>

<div class="cleaner"></div>
</div>

<!-- end of main -->
