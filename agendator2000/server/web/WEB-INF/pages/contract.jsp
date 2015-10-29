<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="templatemo_main">

<script type="text/javascript">

    $(function () {
        $("#edit-div").dialog({
            autoOpen:false,
            height:230,
            width:340,
            modal:true
        });

        $('#sendNewContract').click(function (event) {
            var initDate = $.trim($('#initDate').val());
            var expirationDate = $.trim($('#expirationDate').val());
            var monthlyPayment = parseMoney($.trim($('#monthlyPayment').val()));

            // limpiar errores del submit anterior
            showError('#newContract-msg', '', '#initDate,#expirationDate,#monthlyPayment');

            if (!initDate) {
                showError('#newContract-msg', 'Ingrese una fecha de inicio.', '#initDate');
                return false;
            }else{
                showError('#newContract-msg', '', '#initDate');
            }

            if (!expirationDate) {
                showError('#newContract-msg', 'Ingrese una fecha de fin.', '#expirationDate');
                return false;
            }else{
                showError('#newContract-msg', '', '#expirationDate');
            }

            if (!monthlyPayment) {
                showError('#newContract-msg', 'Ingrese una mensualidad.', '#monthlyPayment');
                return false;
            }else{
                showError('#newContract-msg', '', '#monthlyPayment');
            }

            if(!isNumeric(monthlyPayment)){
                showError('#newContract-msg', 'Ingrese una mensualidad v&aacute;lida.', '#monthlyPayment');
                return false;
            }else{
                showError('#newContract-msg', '', '#monthlyPayment');
            }

            // volver a colocar valores, para sanitizar (algo) lo que ha ingresado el usuario
            $('#initDate').val(initDate);
            $('#expirationDate').val(expirationDate);
            $('#monthlyPayment').val(monthlyPayment);

            $('#newContractForm').submit();

            return false;
        });

        $('#sendOldContract').click(function () {
            var initDate = $.trim($('#edit_initDate').val());
            var expirationDate = $.trim($('#edit_expirationDate').val());
            var monthlyPayment = parseMoney($.trim($('#edit_monthlyPayment').val()));

            // limpiar errores del submit anterior
            showError('#oldContract-msg', '', '#edit_initDate,#edit_expirationDate,#edit_monthlyPayment');

            if (!initDate) {
                showError('#oldContract-msg', 'Ingrese una fecha de inicio.', '#edit_initDate');
                return false;
            }else{
                showError('#oldContract-msg', '', '#edit_initDate');
            }

            if (!expirationDate) {
                showError('#oldContract-msg', 'Ingrese una fecha de fin.', '#edit_expirationDate');
                return false;
            }else{
                showError('#oldContract-msg', '', '#edit_expirationDate');
            }

            if (!monthlyPayment) {
                showError('#oldContract-msg', 'Ingrese una mensualidad.', '#edit_monthlyPayment');
                return false;
            }else{
                showError('#oldContract-msg', '', '#edit_monthlyPayment');
            }

            if(!isNumeric(monthlyPayment)){
                showError('#oldContract-msg', 'Ingrese una mensualidad v&aacute;lida.', '#edit_monthlyPayment');
                return false;
            }else{
                showError('#oldContract-msg', '', '#edit_monthlyPayment');
            }

            // volver a colocar valores, para sanitizar (algo) lo que ha ingresado el usuario
            $('#edit_initDate').val(initDate);
            $('#edit_expirationDate').val(expirationDate);
            $('#edit_monthlyPayment').val(monthlyPayment);

            $('#editForm').submit();

            return false;
        });

        $('#initDate,#expirationDate,#edit_initDate,#edit_expirationDate').datepicker();
        $('#initDate,#expirationDate,#edit_initDate,#edit_expirationDate').datepicker('option', 'dateFormat', 'dd/mm/yy');
    });

    function deleteContract(a, id) {
        if(window.confirm('Realmente desea borrar el contrato con id #'+id+'?')){
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

    function editContract(id, initDate, expirationDate, monthlyPayment) {
        $('#edit_idContract').val("" + id);
        $('#edit_initDate').val(initDate);
        $('#edit_expirationDate').val(expirationDate);
        $('#edit_monthlyPayment').val(monthlyPayment.replace('$','').replace('.',''));

        $("#edit-div").dialog("open");
        $("#edit-div").dialog('option', 'title', 'Editar contrato #'+id);

        return false;
    }

</script>

<div class="col_w900 col_w900_last">

    <p>En esta sección se pueden manejar los contratos del club (ya sea para socios o para personal). Aquí se especifican
        datos del contrato como fecha de inicio/fin y mensualidad. La mensualidad, para el caso se socios, corresponde
        al aporte monetario mensual que realizan para el club; para el caso del personal, corresponde al sueldo que reciben.</p>

    <p>Una vez que un contrato es creado, queda disponible para ser asignado a un personal o socio, en la sección respectiva.
        Una vez que un contrato es asignado a un personal o socio, no puede ser reasignado.</p>

    <div class="col_w100 float_l">

        <div class="post_box" id="contractsDiv">

            <h2>Agregar nuevo contrato</h2>

            <span id="newContract-msg">&nbsp;</span>

            <form id="newContractForm" action="handlePost" method="post">

                <input type="hidden" id="idContract" name="id" value=""/>

                <table>
                    <tr>
                        <td class="centered"><label for="initDate">Fecha de Inicio</label></td>
                        <td>:</td>
                        <td><input id="initDate" name="initDate" type="text" value=""/></td>
                    </tr>
                    <tr>
                        <td class="centered"><label for="expirationDate">Fecha de Fin</label></td>
                        <td>:</td>
                        <td><input id="expirationDate" name="expirationDate" type="text" value=""/></td>
                    </tr>
                    <tr>
                        <td class="centered"><label for="monthlyPayment">Mensualidad (CLP)</label></td>
                        <td>:</td>
                        <td><input id="monthlyPayment" name="monthlyPayment" type="text" value=""/></td>
                    </tr>
                    <tr>
                        <td><input id="sendNewContract" class="button" type="button" value="enviar"/></td>
                        <td>&nbsp;</td>
                        <td><input class="button" type="button" value="limpiar"
                                   onclick="$('#initDate,#expirationDate,#monthlyPayment').val(''); return false;"/></td>
                    </tr>
                </table>

            </form>

            <!-- formulario de edit -->

            <div id="edit-div" title="Editar contrato">

                <span id="oldContract-msg">&nbsp;</span>

                <form id="editForm" action="handlePost" method="post">

                    <input type="hidden" id="edit_idContract" name="id" value="${id}"/>

                    <table>
                        <tr>
                            <td align="center"><label for="edit_initDate">Fecha de Inicio</label></td>
                            <td>:</td>
                            <td><input id="edit_initDate" name="initDate" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td align="center"><label for="edit_expirationDate">Fecha de Fin</label></td>
                            <td>:</td>
                            <td><input id="edit_expirationDate" name="expirationDate" type="text" value=""/></td>
                        </tr>
                        <tr>
                            <td align="center"><label for="edit_monthlyPayment">Mensualidad (CLP)</label></td>
                            <td>:</td>
                            <td><input id="edit_monthlyPayment" name="monthlyPayment" type="text" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td><input id="sendOldContract" class="button" type="submit" value="enviar"/></td>
                            <td>&nbsp;</td>
                            <td><input class="button" type="button" value="cancelar"
                                       onclick="$('#edit-div').dialog('close'); return false;"/></td>
                        </tr>
                    </table>
                </form>
            </div>

            <h2>Contratos ingresados en el sistema</h2>

            Hay <b><span id="counter">${fn:length(availableContracts)}</span></b> contrato(s).

            <display:table name="availableContract" id="contractTable" class="displayTable">
                <display:column property="id" title="ID" class="id_col"/>
                <display:column property="formattedInitDate" title="Fecha Inicio"/>
                <display:column property="formattedExpirationDate" title="Fecha Fin"/>
                <display:column property="formattedMonthlyPayment" title="Mensualidad (CLP)"/>
                <display:column property="formattedHooked2staff" title="Asociado a Personal"/>
                <display:column property="formattedHooked2associate" title="Asociado a Socio"/>
                <display:column title="Editar" class="edit_col">
                    <a href="#"
                       onclick="return editContract(${contractTable.id},'${contractTable.formattedInitDate}','${contractTable.formattedExpirationDate}','${contractTable.monthlyPayment}');">
                        <img src="../../images/edit.png" class="edit-img" alt="Editar" title="Editar" width="16"
                             height="16"/>
                    </a>
                </display:column>
                <display:column title="Borrar" class="delete_col">
                    <a href="#" onclick="return deleteContract(this, ${contractTable.id});">
                        <img src="../../images/delete.png" class="delete-img" alt="Borrar" title="Borrar"/>
                    </a>
                </display:column>
            </display:table>

            <small><b>Nota:</b> los contratos deben crearse antes de crear personal y socio, para que
                aparezcan como opción al momento de crearlos. Al borrar un contrato, será borrado su
            personal ó socio asociado.</small>

            <div class="cleaner"></div>
        </div>

    </div>

    <div class="cleaner"></div>
</div>

<div class="cleaner"></div>
</div>
<!-- end of main -->
