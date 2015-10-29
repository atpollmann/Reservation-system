<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="templatemo_main">

    <script type="text/javascript">
        function deleteContactData(a, id){
            if(window.confirm('Realmente desea borrar el dato de contacto con id #'+id+'?')){
                $.post('handleDeleteContactData', {id:id}, function (data, textStatus, jqXHR) {
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
        function clearFields(){
            $('#contactType').val('1'); //primera opcion, por defecto
            $('#valueData').val(''); // limpiar valor
            return false;
        }
        $(function(){
            $('#sendNewContactData').click(function(){

                var contactType = $.trim($('#contactType').val());
                var valueData = $.trim($('#valueData').val());

                // limpiar errores del submit anterior
                showError('#newContactData-msg', '', '#contactType,#valueData');

                if(!valueData){
                    showError('#newContactData-msg', 'Ingrese un valor.', '#valueData');
                    return false;
                }else if(contactType=='2' && !isPhoneNumber(valueData)){
                    showError('#newContactData-msg', 'Ingrese un número válido (entre 7 y 12 dígitos).', '#valueData');
                    return false;
                }else if(contactType=='3' && !isEmail(valueData)){
                    showError('#newContactData-msg', 'Ingrese un email válido.', '#valueData');
                    return false;
                }else{
                    showError('#newContactData-msg', '', '#valueData');
                }

                $('#newContactDataForm').submit();

                return false;
            });
        });
    </script>

	<div class="col_w900 col_w900_last">
	
		<div class="col_w100 float_l">
		
			<div class="post_box">

                <a href="index.html"><img src="../../images/back.png" width="" height="" alt="volver" title="volver"> volver</a>

                <br/>
                <br/>

                <table class="col_w50 float_l">
                    <tr>
                        <td class="centered">ID</td>
                        <td>:</td>
                        <td><b>${associate.id}</b></td>
                    </tr>
                    <tr>
                        <td class="centered">Nombre</td>
                        <td>:</td>
                        <td><b>${associate.firstName}</b></td>
                    </tr>
                    <tr>
                        <td class="centered">Apellido</td>
                        <td>:</td>
                        <td><b>${associate.lastName}</b></td>
                    </tr>
                    <tr>
                        <td class="centered">Fecha Nacimiento</td>
                        <td>:</td>
                        <td><b>${associate.formattedBirthDate}</b></td>
                    </tr>
                    <tr>
                        <td class="centered">Contrato</td>
                        <td>:</td>
                        <td><b>Contrato #${associate.contract.id}</b></td>
                    </tr>
                    <tr>
                        <td class="centered">Nacionalidad</td>
                        <td>:</td>
                        <td><b>${associate.nationality.country}</b></td>
                    </tr>
                </table>

                <div class="col_w50 float_r">

                    <h3>Agregar nuevo dato de contacto</h3>

                    <span id="newContactData-msg">&nbsp;</span>

                    <form id="newContactDataForm" action="handlePostContactData" method="post">

                        <input type="hidden" id="associateId" name="associateId" value="${associate.id}"/>

                        <table class="col_w100">
                            <tr>
                                <td class="centered"><label for="contactType">Tipo de contacto</label></td>
                                <td>:</td>
                                <td>
                                    <select name="contactType" id="contactType" style="width: 80%;">
                                        <c:forEach var="contactType" items="${availableContactType}">
                                            <option value="${contactType.id}">${contactType.type}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="centered"><label for="valueData">Valor</label></td>
                                <td>:</td>
                                <td><input name="valueData" id="valueData" type="text" value="" style="width: 80%;"/></td>
                            </tr>
                            <tr>
                                <td>
                                    <input id="sendNewContactData" class="button" type="button" value="enviar" style="width:100px;" />
                                </td>
                                <td>&nbsp;</td>
                                <td><input class="button" type="button" style="width:100px;"
                                           value="limpiar" onclick="return clearFields();"/></td>
                            </tr>
                        </table>

                    </form>

                </div>

                <div class="cleaner"></div>

			</div>

		</div>

        <div class="col_w100 float_r">
            <div class="post_box">

                <h2>Datos de contacto de <b>${associate.firstName} ${associate.lastName}</b></h2>

                <div class="col_w100 float_l">

                    Hay <b><span id="counter">${fn:length(availableContactData)}</span></b> dato(s) de contacto.

                    <display:table name="availableContactData" id="contactDataTable" class="displayTable">
                        <display:column property="id" title="ID" class="id_col"/>
                        <display:column property="contactType.type" title="Tipo contacto"/>
                        <display:column property="valueData" title="Valor"/>
                        <display:column title="Borrar" class="delete_col">
                            <a href="#" onclick="return deleteContactData(this, ${contactDataTable.id});">
                                <img src="../../images/delete.png" class="delete-img" alt="Borrar" title="Borrar"/>
                            </a>
                        </display:column>
                    </display:table>

                </div>

                <div class="col_w50 float_r">



                </div>

            </div>
        </div>

        <%--<jsp:include page="lateral.jsp"/>--%>
		
		<div class="cleaner"></div>
	</div>
	
	<div class="cleaner"></div>
</div> <!-- end of main -->
