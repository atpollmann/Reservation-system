<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="templatemo_main">

    <div class="col_w900 col_w900_last">

        <p>
            En esta sección se puede visualizar el reporte financiero del club. Se presenta el valor total de activos
            (considerando jugadores y otros activos); valor total de pasivos (pasivos con estado pendiente) y balance
            total (activos - pasivos pendientes).
        </p>

        <div class="col_w100 float_l">

           <h2>Reporte Financiero</h2>

            <div class="post_box" id="financesDiv">

               <table id="financeTable" class="displayTable">
                   <thead>
                       <tr>
                           <td>Total Activos (CLP)</td>
                           <td>Total Pasivos (CLP)</td>
                           <td>Total Neto (CLP)</td>
                       </tr>
                   </thead>

                   <tr class="odd">
                       <td>${totalActives}</td>
                       <td>${totalPassives}</td>
                       <td>${totalNet}</td>
                   </tr>

               </table>


            </div>

        </div>

    </div>

    <div class="cleaner"></div>
</div>