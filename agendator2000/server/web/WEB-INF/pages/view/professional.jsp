<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
    .fc-agendaWeek-view tr {
        height: 30px;
    }

    .fc-agendaDay-view tr {
        height: 30px;
    }

    #calendar_container .fc-view-container {
        background-color: #ffffff;
    }

    #calendar_container,  {
        height: 800px;
    }
</style>

<script type="application/javascript" src="../../js/lang/es.js"></script>
<script type="application/javascript">
    var calendarContainer = "#calendar_container";
    var dialogConfirm = "#dialog-confirm";
    var sessionStartDate = '${careSession.dates.isoStartDateTime}';
    var sessionEndDate = '${careSession.dates.isoEndDateTime}';
    var selectedCareSession = '${idCareSession}';
    var defaultDate = '${careSession.dates.isoStartDate}';

    function showDialog(message, buttons) {
        $('#modal_message').html(message);
        $(dialogConfirm).dialog({
            resizable: false,
            height:150,
            width: 300,
            modal: true,
            closeOnEscape: false,
            open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
            buttons: buttons
        });
    }

    function setRow(elem) {
        showDialog('Los bloques no guardados (azules) serán quitados. Continuar?', {
            "Cambiar sesión": function() {
                $(dialogConfirm).dialog("close");
                window.location = 'index.html?idCareSession=' + $(elem).find('input').val();
            },
            "Cancelar": function() {
                $('.inputCareSession').removeProp('checked');
                $('.inputCareSession'+selectedCareSession).prop('checked', true);
                $(dialogConfirm).dialog("close");
            }
        });
    }
    function setColor(event, color) {
        event.color = color;
        $(calendarContainer).fullCalendar('renderEvent', event, true);
    }
    function showRemoveEventDialog(calEvent) {
        var oldColor = calEvent.color;
        setColor(calEvent, 'orange');
        showDialog('Quitará una hora del calendario.<br/>Continuar?', {
            "Borrar hora": function() {
                $(calendarContainer).fullCalendar('removeEvents', function (event) {
                    return (event == calEvent);
                });
                updateGridHeight();
                $(dialogConfirm).dialog("close");
            },
            "Cancelar": function() {
                setColor(calEvent, oldColor);
                $(dialogConfirm).dialog("close");
            }
        });
    }

    function updateGridHeight() {
        $('#calendar_container div.fc-time-grid-container').css('height', '720px');
    }

    function saveAvailability() {
        var allEvents = $(calendarContainer).fullCalendar('clientEvents');
        var formContent = '';
        allEvents.forEach(function(el){
            if (el.id != 'available') {
                formContent += (
                    el.id + ',' +
                    datetime_str(el.start) + ',' +
                    datetime_str(el.end) + ';'
                );
            }
        });
        $('#allSchedules').val(formContent);
        return true;
    }

    $(document).ready(function() {
        function addEvent(eventData) {
            $(calendarContainer).fullCalendar('renderEvent', eventData, true);
            $(calendarContainer).fullCalendar('unselect');
        }

        $(calendarContainer).fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultView: 'agendaWeek',
            views: {
                doubleWeekSantiago:{
                    type:'agendaWeek',
                    duration: { days:${careSession.dates.daysDuration} }
                }
            },
            businessHours:false,
            defaultDate: defaultDate,
            selectable: true,
            editable: true,
            eventOverlap: function(stillEvent, movingEvent) {
                return (stillEvent.id == 'available');
            },
            select: function(start, end, allDay) {
                var startDateTimeStr = datetime_str(start);
                var endDateTimeStr = datetime_str(start);

                var startDateStr = date_str(start);
                var endDateStr = date_str(start);

                if(startDateStr != endDateStr){
                    $(calendarContainer).fullCalendar('unselect') ;
                    return;
                }
                // If out of range (sessionStartDate,sessionEndDate)
                if (!(sessionStartDate <= startDateTimeStr && startDateTimeStr <= sessionEndDate &&
                        sessionStartDate <= endDateTimeStr && endDateTimeStr <= sessionEndDate)) {
                    return;
                }
                var start2 = start.clone(); start2.add(15, 'minutes');

                var allEvents = $(calendarContainer).fullCalendar('clientEvents').filter(function(el) {
                    return (el.id != 'available');
                });

                var firstEvent = my_find(allEvents, start, function(el) {
                    return start.isSame(el.start);
                });

                if (firstEvent) {
                    return;
                }

                addEvent({
                    id:'',
                    constraint: 'available',
                    title: 'Disponible',
                    start: start,
                    end: start2,
                    color: 'blue'
                });

                updateGridHeight();

            },
            eventClick: function(calEvent, jsEvent, view) {
                showRemoveEventDialog(calEvent);
            },
            timeFormat: 'HH:mm',
            minTime: '07:00:00',
            maxTime: '19:00:00',
            events: [
                // light green background, for limiting session limits
                {
                    id: 'available',
                    start: sessionStartDate,
                    end: sessionEndDate,
                    rendering: 'background'
                }
                // database saved schedules
                <c:forEach items="${calendar.freeSchedules}" var="sched">
                ,${sched.jsonEventFormattedFree}
                </c:forEach>

                // database taken appointments
                <c:forEach items="${calendar.takenAppointments}" var="pair">
                ,${pair.schedule.jsonEventFormattedTaken}
                </c:forEach>
            ],
            allDaySlot: false,
            snapDuration: '00:15:00',
            slotLabelFormat: 'HH:mm',
            businessHours: {
                start: '8:00',
                end: '18:00',
                dow: [1, 2, 3, 4, 5]
            }
        });

        updateGridHeight();
    });
</script>
<div class="rounded_corners">
    <h3 style="text-align: left;">Sesión de atención</h3>
    <table class="table">
        <thead>
            <td>&nbsp;</td>
            <td>#</td>
            <td>ONG</td>
            <td>Lugar</td>
            <td>Dirección</td>
            <td>Inicio</td>
            <td>Término</td>
        </thead>
        <tbody>
            <c:forEach items="${careSessions}" var="careSession">
            <tr onclick="setRow(this);">
                <td>
                    <input
                        type="radio"
                        name="idCareSession"
                        class="inputCareSession${careSession.id}"
                        <c:if test="${careSession.id == idCareSession}">checked="checked"</c:if>
                        value="${careSession.id}"
                    />
                <td>${careSession.id}</td>
                <td>${careSession.ong.name}</td>
                <td>${careSession.location}</td>
                <td>${careSession.address}</td>
                <td>${careSession.dates.startDate}<br/><sub>${careSession.dates.startTime}</sub></td>
                <td>${careSession.dates.endDate}<br/><sub>${careSession.dates.endTime}</sub></td>
            </tr>
            </c:forEach>
        </tbody>
    </table>

    <h3 style="text-align: left;">Horas Disponibles</h3>
    <sub>Click en la zona verde para ingresar bloques horarios. También puede arrastrar y redimensionar.</sub>
    <br/>
    <sub>
        <span style="color:blue;font-weight:bold;">Azul: bloques nuevos</span>
        <span style="color:darkgreen;font-weight:bold;">Verde: bloques libres</span>
        <span style="color:darkred;font-weight:bold;">Rojo: bloques con citas</span>
    </sub>
    <div id="dialog-confirm" title="Cancelar hora" style="display: none;">
        <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span id="modal_message">&nbsp;</span></p>
    </div>
    <div id="calendar_container"></div>
    <br/>
    <br/>
    <form name="f" role="form" method="post" action="updateSchedule">
        <input type="hidden" name="idCareSession" value="${idCareSession}"/>
        <input id="allSchedules" type="hidden" name="allSchedules" value=""/>
        <button type="submit" class="btn btn-default" onclick="return saveAvailability();">Guardar disponibilidad</button>
    </form>
</div>