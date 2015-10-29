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

        $('#sendNewActive').click(function (event) {
            var value = $.trim($('#value').val());
            var idActiveType = $.trim($('#type').val());

            // limpiar errores del submit anterior
            showError('#newActive-msg', '', '#value,#type');

            if (!value) {
                showError('#newActive-msg', 'Ingrese un Valor.', '#value');
                return false;
            } else {
                showError('#newActive-msg', '', '#value');
            }

            if(!isNumeric(value)){
                showError('#newActive-msg', 'Debe ingresar un numero', '#value');
                return false;
            } else {
                showError('#newActive-msg', '', '#edit_value');
            }

            if (!idActiveType) {
                showError('#newActive-msg', 'Debe seleccionar un tipo de activo.', '#type');
                return false;
            } else {
                showError('#newActive-msg', '', '#type');
            }

            // volver a colocar valores, para sanitizar (algo) lo que ha ingresado el usuario
            $('#value').val(value);
            $('#type').val(idActiveType);

            $('#newActiveForm').submit();

            return false;
        });

        $('#sendOldActive').click(function () {
            var value = $.trim($('#edit_value').val());
            var idActiveType = $.trim($('#edit_type').val());

            // limpiar errores del submit anterior
            showError('#oldActive-msg', '', '#edit_value,#edit_status');

            if (!value) {
                showError('#oldActive-msg', 'Ingrese un Valor', '#edit_value');
                return false;
            } else {
                showError('#oldActive-msg', '', '#edit_value');
            }

            if(!isNumeric(value)){
                showError('#oldActive-msg', 'Debe ingresar un numero', '#edit_value');
                return false;
            } else {
                showError('#oldActive-msg', '', '#edit_value');
            }

            if (!idActiveType) {
                showError('#oldActive-msg', 'Debe seleccionar un tipo de activo.', '#edit_type');
                return false;
            } else {
                showError('#oldActive-msg', '', '#edit_type');
            }

            $('#edit_value').val(value);
            $('#edit_type').val(idActiveType);

            $('#editForm').submit();

            return false;
        });
    });

    function deleteActive(a, id) {
        if(window.confirm('Realmente desea borrar el activo con id #'+id+'?')){
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

    function editActive(id, username, idType) {
        $('#edit_idActive').val("" + id);
        $('#edit_value').val(username);
        $('#edit_type').val(idType);
        $("#edit-div").dialog('option', 'title', 'Editar activo #'+id);
        $("#edit-div").dialog("open");

        return false;
    }

</script>

<div class="col_w900 col_w900_last">

    <p>En esta sección se pueden manejar los activos del club. Aquí deben ingresarse los bienes
        como por ejemplo el estadio, bienes raíces diversos, campos de entrenamiento, etc.</p>

    <div class="col_w50 float_l">

        <div class="post_box" id="activeDiv">

            <h2>Activos ingresados en el sistema</h2>

            Hay <b><span id="counter">${fn:length(availableActive)}</span></b> activo(s).


            <display:table name="availableActive" id="activeTable"  class="displayTable">
                <display:column property="id" title="ID" class="id_col"/>
                <display:column property="formattedValue" title="Valor (CLP)"/>
                <display:column property="activeType.type" title="Tipo"/>
                <display:column title="Editar" class="edit_col">
                    <a href="#"
                       onclick="return editActive(${activeTable.id},'${activeTable.value}','${activeTable.activeType.id}');">
                        <img src="../../images/edit.png" class="edit-img" alt="Editar" title="Editar" width="16"
                             height="16"/>
                    </a>
                </display:column>
                <display:column title="Borrar" class="delete_col">
                    <a href="#" onclick="return deleteActive(this, ${activeTable.id});">
                        <img src="../../images/delete.png" class="delete-img" alt="Borrar" title="Borrar"/>
                    </a>
                </display:column>
            </display:table>

        </div>

    </div>

    <div class="col_w50 float_r">
        <div class="post_box">

            <h2>Agregar nuevo activo</h2>

            <span id="newActive-msg">&nbsp;</span>

            <form id="newActiveForm" action="handlePost" method="post">

                <input type="hidden" id="idActive" name="id" value=""/>

                <table>
                    <tr>
                        <td class="centered"><label for="value">Valor (CLP)</label></td>
                        <td>:</td>
                        <td><input id="value" name="value" type="text" value=""/></td>
                    </tr>
                    <tr id="type-row">
                        <td class="centered"><label for="type">Tipo Activo</label></td>
                        <td>:</td>
                        <td>
                            <select name="type" id="type" style="width: 100%;">
                                <c:forEach var="type" items="${availableActiveType}">
                                    <option value="${type.id}">${type.type}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><input id="sendNewActive" class="button" type="button" value="enviar"/></td>
                        <td>&nbsp;</td>
                        <td><input class="button" type="button" value="limpiar"
                                   onclick="$('#value,#type').val(''); return false;"/></td>
                    </tr>
                </table>

            </form>

            <!-- formulario de edit -->

            <div id="edit-div" title="Editar activo">

                <span id="oldActive-msg">&nbsp;</span>

                <form id="editForm" action="handlePost" method="post">

                    <input type="hidden" id="edit_idActive" name="id"
                           value="<c:out value='${id}'/>"/>

                    <table>
                        <tr>
                            <td align="center"><label for="edit_value">Valor (CLP)</label></td>
                            <td>:</td>
                            <td><input id="edit_value" name="value" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td class="centered"><label for="edit_type">Tipo Activo</label></td>
                            <td>:</td>
                            <td>
                                <select name="type" id="edit_type" style="width: 100%;">
                                    <c:forEach var="type" items="${availableActiveType}">
                                        <option value="${type.id}">${type.type}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><input id="sendOldActive" class="button" type="submit" value="enviar"/></td>
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
