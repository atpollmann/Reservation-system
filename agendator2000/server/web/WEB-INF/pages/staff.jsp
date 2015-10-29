<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="templatemo_main">
    <script type="text/javascript">
        function clearFields(){
            $('#firstName,#lastName,#birthDate,#baseValue').val('');
            $('#hired').val('false');
            $('#staffType,#nationality').val('1');
            $('#contract').val('');
            $('.baseValue-row').hide();
            return false;
        }

        function validateFields(form, span, firstName, lastName, birthDate, hired, staffType, nationality, contract, baseValue){
            var valFirstName = $.trim($(firstName).val());
            var valLastName = $.trim($(lastName).val());
            var valBirthDate = $.trim($(birthDate).val());
            var valHired = $.trim($(hired).val());
            var valStaffType = $.trim($(staffType).val());
            var valNationality = $.trim($(nationality).val());
            var valContract = $.trim($(contract).val());
            var valBaseValue = $.trim($(baseValue).val());

            // limpiar errores del submit anterior
            showError(span, '', firstName);
            showError(span, '', lastName);
            showError(span, '', birthDate);       // solo estos pueden estar resaltados en rojo
            showError(span, '', contract);
            showError(span, '', baseValue);

            // primer nombre
            if(!valFirstName){
                showError(span, 'Debe ingresar un primer nombre.', firstName);
                return false;
            }else{
                showError(span, '', firstName);
            }

            if(!valLastName){
                showError(span, 'Debe ingresar un apellido.', lastName);
                return false;
            }else{
                showError(span, '', lastName);
            }

            if(!valBirthDate){
                showError(span, 'Debe ingresar una fecha.', birthDate);
                return false;
            }else{
                showError(span, '', birthDate);
            }

            if(valContract == ''){
                showError(span, 'El contrato no puede estar vacío.', contract);
                return false;
            }else{
                showError(span, '', contract);
            }

            switch(valStaffType){
                case '5': //arquero
                case '6': //defensa
                case '7': //mediocampista
                case '8': // delantero
                        if(!valBaseValue){
                            showError(span, 'Debe ingresar un valor base (CLP).', baseValue);
                            return false;
                        }else{
                            showError(span, '', baseValue);
                        }

                        if(!isNumeric(valBaseValue)){
                            showError(span, 'El valor ingresado no es entero.', baseValue);
                            return false;
                        }else{
                            showError(span, '', baseValue);
                        }

                    break;
                default:
                    showError(span, '', baseValue);
                    break;
            }

            // all ok, submit
            $(form).submit();

            return false;
        }

        function add_change_check(select, div2hide){
            $(select).change(function(){
                var type = $.trim($(this).val().toLowerCase());
                switch(type){
                    case '5': //arquero
                    case '6': // defensa
                    case '7': // mediocampista
                    case '8': //delantero
                        $(div2hide).show(); break;
                    default:
                        $(div2hide).hide(); break;
                }
            });
        }

        $(function(){
            $("#edit-div").dialog({
                autoOpen:false,
                height:365,
                width:325,
                modal:true
            });

            $('.baseValue-row, #edit_baseValue-row').hide();

            add_change_check('#staffType', '.baseValue-row');
            add_change_check('#edit_staffType', '#edit_baseValue-row');

            $('#birthDate,#edit_birthDate').datepicker();
            $('#birthDate,#edit_birthDate').datepicker('option', 'dateFormat', 'dd/mm/yy');
        });

        function editStaff(id,firstName,lastName, birthDate,hired,baseValue, nationality,staffType,contract){

            // agregar contrato actual, solo si es la primera edicion
            if($('#lastContract').length == 0){
                $('#edit_contract').append($('<option>', {
                    id: 'lastContract',
                    value: contract,
                    text: 'Contrato #'+contract
                }));
            }

            $('#edit_idStaff').val(""+id);
            $('#edit_firstName').val(firstName);
            $('#edit_lastName').val(lastName);
            $('#edit_birthDate').val(birthDate);
            $('#edit_hired').val(hired ? 'true' : 'false');
            $('#edit_baseValue').val(""+baseValue);
            $('#edit_nationality').val(""+nationality);
            $('#edit_staffType').val(""+staffType);
            $('#edit_contract').val(""+contract);

            $('#edit_staffType').change();

            $("#edit-div").dialog('option', 'title', 'Editar personal con id #'+id);
            $('#edit-div').dialog('open');

            return false;
        }

        function deleteStaff(a, id){
            if(window.confirm('Realmente desea borrar el personal con id #'+id+'?')){
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

    </script>

	<div class="col_w900 col_w900_last">

        <p>En esta sección se puede manejar el personal del club. Se deben ingresar datos básicos (nombre, apellido, etc.),
            así como se debe asociar a un contrato pre ingresado.</p>

		<div class="col_w100 float_l">
		
			<div class="post_box">

                <h2>Agregar nuevo personal</h2>

                <c:if test="${empty availableContracts}">
                    No hay contratos disponibles. Para poder agregar personal, primero debe <a href="../contract/index.html">agregar contratos</a>.
                </c:if>

                <c:if test="${not empty availableContracts}">

                    <span id="newStaff-msg">&nbsp;</span>

                    <form id="newStaffForm" action="handlePost" method="post">

                        <input type="hidden" id="idStaff" name="id" value="" />

                        <table class="col_w100">
                            <tr>
                                <!-- nombre -->
                                <td class="centered"><label for="firstName">Nombre</label></td>
                                <td>:</td>
                                <td><input name="firstName" id="firstName" type="text" value="" style="width: 80%;"/></td>

                                <!-- tipo personal -->
                                <td class="centered"><label for="staffType">Tipo de personal</label></td>
                                <td>:</td>
                                <td>
                                    <select name="staffType" id="staffType" style="width: 80%;">
                                        <c:forEach var="staffType" items="${availableStaffType}">
                                            <option value="${staffType.id}">${staffType.type}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <!-- apellido -->
                                <td class="centered"><label for="lastName">Apellido</label></td>
                                <td>:</td>
                                <td><input name="lastName" id="lastName" type="text" value="" style="width: 80%;"/></td>

                                <!-- nacionalidad -->
                                <td class="centered"><label for="nationality">Nacionalidad</label></td>
                                <td>:</td>
                                <td>
                                    <select name="nationality" id="nationality" style="width: 80%;">
                                        <c:forEach var="nationality" items="${availableNationality}">
                                            <option value="${nationality.id}">${nationality.country}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <!-- fecha nacimiento -->
                                <td class="centered"><label for="birthDate">Fecha de nacimiento</label></td>
                                <td>:</td>
                                <td><input name="birthDate" id="birthDate" type="text" value="" style="width: 80%;"/></td>

                                <!-- contrato -->
                                <td class="centered"><label for="contract">Contrato</label></td>
                                <td>:</td>
                                <td>
                                    <select name="contract" id="contract" style="width: 80%;">
                                        <c:forEach var="contract" items="${availableContracts}">
                                            <option value="${contract.id}">Contrato #${contract.id}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <!-- contratado ? -->
                                <td class="centered"><label for="hired">Contratado</label></td>
                                <td>:</td>
                                <td><select name="hired" id="hired" style="width: 80%;">
                                    <option value="false">No</option>
                                    <option value="true">Sí</option>
                                </select></td>

                                <!-- valor base -->
                                <td class="baseValue-row" class="centered"><label for="baseValue">Valor base (CLP)</label></td>
                                <td class="baseValue-row">:</td>
                                <td class="baseValue-row"><input name="baseValue" id="baseValue" value="" style="width: 80%;"/></td>
                            </tr>

                            <tr>
                                <td colspan="2">
                                    <input id="sendNewStaff" class="button" type="button" value="enviar"
                                           style="width:100px;"
                                           onclick="return validateFields('#newStaffForm', '#newStaff-msg', '#firstName', '#lastName', '#birthDate', '#hired', '#staffType', '#nationality', '#contract', '#baseValue');" />
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td colspan="2"><input class="button" type="button"
                                           style="width:100px;"
                                           value="limpiar" onclick="return clearFields();"/></td>
                            </tr>
                        </table>

                    </form>

                </c:if>

                <!-- formulario de edit -->
                <div id="edit-div" title="Editar personal">

                    <span id="oldStaff-msg">&nbsp;</span>

                    <form id="oldStaffForm" action="handlePost" method="post">

                        <input type="hidden" id="edit_idStaff" name="id" value="" />

                        <table>
                            <tr>
                                <td class="centered"><label for="edit_firstName">Nombre</label></td>
                                <td>:</td>
                                <td><input name="firstName" id="edit_firstName" type="text" value="" style="width: 80%;"/></td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_lastName">Apellido</label></td>
                                <td>:</td>
                                <td><input name="lastName" id="edit_lastName" type="text" value="" style="width: 80%;"/></td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_birthDate">Fecha de nacimiento</label></td>
                                <td>:</td>
                                <td><input name="birthDate" id="edit_birthDate" type="text" value="" style="width: 80%;"/></td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_hired">Contratado</label></td>
                                <td>:</td>
                                <td><select name="hired" id="edit_hired" style="width: 80%;">
                                    <option value="false">No</option>
                                    <option value="true">Sí</option>
                                </select></td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="edit_staffType">Tipo de personal</label></td>
                                <td>:</td>
                                <td>
                                    <select name="staffType" id="edit_staffType" style="width: 80%;">
                                        <c:forEach var="staffType" items="${availableStaffType}">
                                            <option value="${staffType.id}">${staffType.type}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <td class="centered"><label for="edit_nationality">Nacionalidad</label></td>
                                <td>:</td>
                                <td>
                                    <select name="nationality" id="edit_nationality" style="width: 80%;">
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
                                    <select name="contract" id="edit_contract" style="width: 80%;">
                                        <option value="">N/A</option>
                                        <c:forEach var="contract" items="${availableContracts}">
                                            <option value="${contract.id}">Contrato #${contract.id}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>

                            <tr id="edit_baseValue-row">
                                <td class="centered"><label for="edit_baseValue">Valor base (CLP)</label></td>
                                <td>:</td>
                                <td><input name="baseValue" id="edit_baseValue" value=""  style="width: 80%;"/></td>
                            </tr>

                            <tr>
                                <td>
                                    <input id="sendOldStaff" style="width:100px;" class="button" type="button" value="enviar"
                                           onclick="return validateFields('#oldStaffForm', '#oldStaff-msg', '#edit_firstName', '#edit_lastName', '#edit_birthDate', '#edit_hired', '#edit_staffType', '#edit_nationality', '#edit_contract', '#edit_baseValue');" />
                                </td>
                                <td>&nbsp;</td>
                                <td>
                                    <input class="button" type="button" value="cancelar" onclick="$('#edit-div').dialog('close'); return false;"/>
                                </td>
                            </tr>
                        </table>

                    </form>

                    <div class="cleaner"></div>
                </div>

                <h2>Personal ingresado en el sistema</h2>

                Hay <b><span id="counter">${fn:length(availableStaff)}</span></b> personal(es).

                <display:table name="availableStaff" id="staffTable" class="displayTable">

                    <display:column property="id" title="ID"  class="id_col"/>
                    <display:column property="firstName" title="Nombre"/>
                    <display:column property="lastName" title="Apellido"/>
                    <display:column property="formattedBirthDate" title="Fecha Nacimiento"/>
                    <display:column property="formattedHired" title="Contratado"/>
                    <display:column property="formattedBaseValue" title="Valor base (CLP)"/>

                    <display:column property="nationality.country" title="Nacionalidad"/>
                    <display:column property="staffType.type" title="Tipo Personal"/>
                    <display:column property="contract.id" title="ID Contrato"/>

                    <display:column title="Editar" class="edit_col">
                        <a href="#"
                           onclick="return editStaff(${staffTable.id},'${staffTable.firstName}','${staffTable.lastName}',
                                   '${staffTable.formattedBirthDate}',${staffTable.hired}, ${staffTable.baseValue},
                                   ${staffTable.nationality.id}, ${staffTable.staffType.id}, ${staffTable.contract.id});">
                            <img src="../../images/edit.png" class="edit-img" alt="Editar" title="Editar" width="16"
                                 height="16"/>
                        </a>
                    </display:column>
                    <display:column title="Borrar" class="delete_col">
                        <a href="#" onclick="return deleteStaff(this, ${staffTable.id});">
                            <img src="../../images/delete.png" class="delete-img" alt="Borrar" title="Borrar"/>
                        </a>
                    </display:column>

                </display:table>

                <small><b>Nota:</b> al borrar un personal, su contrato asociado será borrado igualmente.</small>

                <div class="cleaner"></div>

            </div>

        </div>

		<div class="cleaner"></div>
	</div>
	
	<div class="cleaner"></div>

</div> <!-- end of main -->
