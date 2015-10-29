<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="templatemo_main">

    <script type="text/javascript">
        $(function(){
            $("#edit-div").dialog({
                autoOpen:false,
                height:310,
                width:340,
                modal:true
            });
            $('#birthDate,#edit_birthDate').datepicker();
            $('#birthDate,#edit_birthDate').datepicker('option', 'dateFormat', 'dd/mm/yy');

            $('#sendNewAssociate').click(function (event) {
                var firstName = $.trim($('#firstName').val());
                var lastName = $.trim($('#lastName').val());
                var birthDate = $.trim($('#birthDate').val());
                var seatRight = $.trim($('#seatRight').val());
                var nationality = $.trim($('#nationality').val());
                var contract = $.trim($('#contract').val());

                // limpiar errores del submit anterior
                showError('#newAssociate-msg', '', '#firstName,#lastName,#birthDate,#seatRight,#contract,#nationality');

                if (!firstName) {
                    showError('#newAssociate-msg', 'Ingrese un primer nombre.', '#firstName');
                    return false;
                }else{
                    showError('#newAssociate-msg', '', '#firstName');
                }

                if (!lastName) {
                    showError('#newAssociate-msg', 'Ingrese un apellido.', '#lastName');
                    return false;
                }else{
                    showError('#newAssociate-msg', '', '#lastName');
                }

                if (!birthDate) {
                    showError('#newAssociate-msg', 'Ingrese una fecha de nacimiento.', '#birthDate');
                    return false;
                }else{
                    showError('#newAssociate-msg', '', '#birthDate');
                }

                if(!nationality){
                    showError('#newAssociate-msg', 'Debe seleccionar una nacionalidad.', '#nationality');
                    return false;
                }else{
                    showError('#newAssociate-msg', '', '#nationality');
                }

                if(!contract){
                    showError('#newAssociate-msg', 'El contrato no puede estar vacío.', '#contract');
                    return false;
                }else{
                    showError('#newAssociate-msg', '', '#contract');
                }

                // volver a colocar valores, para sanitizar (algo) lo que ha ingresado el usuario
                $('#firstName').val(firstName);
                $('#lastName').val(lastName);
                $('#birthDate').val(birthDate);

                // all ok, send form
                $('#newAssociateForm').submit();

                return false;
            });


            $('#sendOldAssociate').click(function (event) {
                var firstName = $.trim($('#edit_firstName').val());
                var lastName = $.trim($('#edit_lastName').val());
                var birthDate = $.trim($('#edit_birthDate').val());
                var seatRight = $.trim($('#edit_seatRight').val());
                var nationality = $.trim($('#edit_nationality').val());
                var contract = $.trim($('#edit_contract').val());

                // limpiar errores del submit anterior
                showError('#oldAssociate-msg', '', '#edit_firstName,#edit_lastName,#edit_birthDate,#edit_seatRight,#edit_nationality,#edit_contract');

                if (!firstName) {
                    showError('#oldAssociate-msg', 'Ingrese un primer nombre.', '#edit_firstName');
                    return false;
                }else{
                    showError('#oldAssociate-msg', '', '#edit_firstName');
                }

                if (!lastName) {
                    showError('#oldAssociate-msg', 'Ingrese un apellido.', '#edit_lastName');
                    return false;
                }else{
                    showError('#oldAssociate-msg', '', '#edit_lastName');
                }

                if (!birthDate) {
                    showError('#oldAssociate-msg', 'Ingrese una fecha de nacimiento.', '#edit_birthDate');
                    return false;
                }else{
                    showError('#oldAssociate-msg', '', '#edit_birthDate');
                }

                if(!nationality){
                    showError('#newAssociate-msg', 'Debe seleccionar una nacionalidad.', '#edit_nationality');
                    return false;
                }else{
                    showError('#newAssociate-msg', '', '#edit_nationality');
                }

                if(!contract){
                    showError('#oldAssociate-msg', 'Ingrese una fecha de nacimiento.', '#edit_contract');
                    return false;
                }else{
                    showError('#oldAssociate-msg', '', '#edit_contract');
                }

                // volver a colocar valores, para sanitizar (algo) lo que ha ingresado el usuario
                $('#edit_firstName').val(firstName);
                $('#edit_lastName').val(lastName);
                $('#edit_birthDate').val(birthDate);

                // all ok, send form
                $('#oldAssociateForm').submit();

                return false;
            });
        });

        function clearFields(){
            $('#firstName,#lastName,#birthDate,#contract').val('');
            $('#seatRight').val('false');
            return false;
        }
        function deleteAssociate(a, id){
            if(window.confirm('Realmente desea borrar el socio con id #'+id+'?')){
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
        function editAssociate(id, firstName, lastName, birthDate, seatRight, nationality, contract){
            // agregar contrato actual, solo si es la primera edicion
            if($('#lastContract').length == 0){
                $('#edit_contract').append($('<option>', {
                    id: 'lastContract',
                    value: contract,
                    text: 'Contrato #'+contract
                }));
            }

            $('#edit_idAssociate').val(""+id);
            $('#edit_firstName').val(firstName);
            $('#edit_lastName').val(lastName);
            $('#edit_birthDate').val(birthDate);
            $('#edit_seatRight').val(seatRight);
            $('#edit_nationality').val(nationality);
            $('#edit_contract').val(contract);

            $("#edit-div").dialog('option', 'title', 'Editar socio #'+id);
            $('#edit-div').dialog('open');

            return false;
        }
    </script>

	<div class="col_w900 col_w900_last">

        <p>En esta sección se pueden manejar los socios del club. Los socios del club pueden ser de 2 tipos: socios con
            derecho a asiento o simpatizantes (sin derecho a asiento). Los socios tienen datos personales (nombre, apellido,
            fecha  de  nacimiento  y  nacionalidad),  además  de  datos  de  contacto  que  pueden  ser  de  varios  tipos  (dirección,
            teléfono o email).</p>

		<div class="col_w100 float_l">
		
			<div class="post_box">

                <h2>Agregar nuevo socio</h2>

                <c:if test="${empty availableContracts}">
                    No hay contratos disponibles. Para poder agregar socios, primero debe <a href="../contract/index.html">agregar contratos</a>.
                </c:if>

                <c:if test="${not empty availableContracts}">

                    <span id="newAssociate-msg">&nbsp;</span>

                    <form id="newAssociateForm" action="handlePost" method="post">

                        <input type="hidden" id="idAssociate" name="id" value=""/>

                        <table class="col_w100">
                            <tr>
                                <!-- nombre -->
                                <td class="centered"><label for="firstName">Nombre</label></td>
                                <td>:</td>
                                <td><input id="firstName" name="firstName" type="text" value=""/></td>

                                <!-- derecho asiento -->
                                <td class="centered"><label for="seatRight">Derecho a Asiento</label></td>
                                <td>:</td>
                                <td>
                                    <select name="seatRight" id="seatRight" style="width: 100%;">
                                        <option value="false">No</option>
                                        <option value="true">Sí</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <!-- apellido -->
                                <td class="centered"><label for="lastName">Apellido</label></td>
                                <td>:</td>
                                <td><input id="lastName" name="lastName" type="text" value=""/></td>

                                <!-- nacionalidad -->
                                <td class="centered"><label for="nationality">Nacionalidad</label></td>
                                <td>:</td>
                                <td>
                                    <select name="nationality" id="nationality" style="width: 100%;">
                                        <c:forEach var="nationality" items="${availableNationality}">
                                            <option value="${nationality.id}">${nationality.country}</option>
                                        </c:forEach>
                                    </select>
                                </td>

                            </tr>
                            <tr>
                                <!-- fecha nacimiento -->
                                <td class="centered"><label for="birthDate">Fecha de Nacimiento</label></td>
                                <td>:</td>
                                <td><input id="birthDate" name="birthDate" type="text" value=""/></td>

                                <!-- contrato -->
                                <td class="centered"><label for="contract">Contrato</label></td>
                                <td>:</td>
                                <td>
                                    <select name="contract" id="contract" style="width: 100%;">
                                        <c:forEach var="contract" items="${availableContracts}">
                                            <option value="${contract.id}">Contrato #${contract.id}</option>
                                        </c:forEach>
                                    </select>
                                </td>

                            </tr>

                            <tr>
                                <td colspan="2"><input id="sendNewAssociate" class="button" type="button" value="enviar"/></td>
                                <td colspan="2">&nbsp;</td>
                                <td colspan="2"><input class="button" type="button" value="limpiar"
                                                       onclick="return clearFields();"/></td>
                            </tr>
                        </table>

                    </form>

                </c:if>

                <!-- formulario de edit -->

                <div id="edit-div" title="Editar socio">

                    <span id="oldAssociate-msg">&nbsp;</span>

                    <form id="oldAssociateForm" action="handlePost" method="post">

                        <input type="hidden" id="edit_idAssociate" name="id" value=""/>

                        <table>
                            <tr>
                                <td class="centered"><label for="edit_firstName">Nombre</label></td>
                                <td>:</td>
                                <td><input id="edit_firstName" name="firstName" type="text" value=""/></td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_lastName">Apellido</label></td>
                                <td>:</td>
                                <td><input id="edit_lastName" name="lastName" type="text" value=""/></td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_birthDate"><small>Fecha de Nacimiento</small></label></td>
                                <td>:</td>
                                <td><input id="edit_birthDate" name="birthDate" type="text" value=""/></td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_seatRight"><small>Derecho a Asiento</small></label></td>
                                <td>:</td>
                                <td>
                                    <select name="seatRight" id="edit_seatRight" style="width: 100%;">
                                        <option value="false">No</option>
                                        <option value="true">Sí</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_nationality">Nacionalidad</label></td>
                                <td>:</td>
                                <td>
                                    <select name="nationality" id="edit_nationality" style="width: 100%;">
                                        <c:forEach var="nationality" items="${availableNationality}">
                                            <option value="${nationality.id}">${nationality.country}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_contract">Contrato</label></td>
                                <td>:</td>
                                <td>
                                    <select name="contract" id="edit_contract" style="width: 100%;">
                                        <option value="">N/A</option>
                                        <c:forEach var="contract" items="${availableContracts}">
                                            <option value="${contract.id}">Contrato #${contract.id}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td><input id="sendOldAssociate" class="button" type="button" value="enviar"/></td>
                                <td>&nbsp;</td>
                                <td><input class="button" type="button" value="cancelar"
                                           onclick="$('#edit-div').dialog('close'); return false;"/></td>
                            </tr>
                        </table>

                    </form>

                </div>

                <h2>Socios ingresados en el sistema</h2>

                Hay <b><span id="counter">${fn:length(availableAssociates)}</span></b> socios(s).

                <display:table name="availableAssociate" id="associateTable"  class="displayTable">
                    <display:column property="id" title="ID" class="id_col"/>
                    <display:column property="firstName" title="Nombre"/>
                    <display:column property="lastName" title="Apellido"/>
                    <display:column property="formattedBirthDate" title="Fecha Nacimiento"/>
                    <display:column property="formattedSeatRight" title="Derecho Asiento"/>
                    <display:column property="nationality.country" title="Nacionalidad"/>
                    <display:column property="contract.id" title="ID Contrato"/>
                    <display:column title="Contacto">
                        <a href="contactData.html?associateId=${associateTable.id}">
                            <img src="../../images/contact.png" class="edit-img" alt="Contacto" title="Contacto" width="16" height="16"/>
                        </a>
                    </display:column>
                    <display:column title="Editar" class="edit_col">
                        <a href="#"
                           onclick="return editAssociate(${associateTable.id},'${associateTable.firstName}',
                           '${associateTable.lastName}','${associateTable.formattedBirthDate}',
                            '${associateTable.seatRight}', ${associateTable.contract.id}, ${associateTable.nationality.id});">
                            <img src="../../images/edit.png" class="edit-img" alt="Editar" title="Editar" width="16" height="16"/>
                        </a>
                    </display:column>
                    <display:column title="Borrar" class="delete_col">
                        <a href="#" onclick="return deleteAssociate(this, ${associateTable.id});">
                            <img src="../../images/delete.png" class="delete-img" alt="Borrar" title="Borrar"/>
                        </a>
                    </display:column>
                </display:table>

                <small><b>Nota:</b> al borrar un socio, su contrato asociado será borrado igualmente.</small>

				<div class="cleaner"></div>
			</div>

		</div>

        <%--<jsp:include page="lateral.jsp"/>--%>
		
		<div class="cleaner"></div>
	</div>
	
	<div class="cleaner"></div>
</div> <!-- end of main -->
