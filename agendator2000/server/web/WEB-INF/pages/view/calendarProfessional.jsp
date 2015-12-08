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
    var appointmentsStartDate = '${calendar.dates.isoStartDateTime}';
    var appointmentsEndDate = '${calendar.dates.isoEndDateTime}';
    var defaultDate = '${calendar.dates.isoStartDate}';

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

    function setColor(event, color) {
        event.color = color;
        $(calendarContainer).fullCalendar('renderEvent', event, true);
    }
    function showRemoveEventDialog(calEvent) {
        var oldColor = calEvent.color;
        setColor(calEvent, 'orange');
        showDialog('Cancelará la cita.<br/>Continuar?', {
            "Cancelar Cita" : function() {
                $(dialogConfirm).dialog("close");
                window.location = 'cancelAppointment?id=' + calEvent.id;
            },
            "Cerrar": function() {
                setColor(calEvent, oldColor);
                $(dialogConfirm).dialog("close");
            }
        });
    }

    function updateGridHeight() {
        $('#calendar_container div.fc-time-grid-container').css('height', '720px');
    }

    $(document).ready(function() {
        $(calendarContainer).fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultView: 'month',
            views: {
                doubleWeekSantiago:{
                    type:'agendaWeek',
                    duration: { days:${calendar.dates.daysDuration} }
                }
            },
            businessHours:false,
            defaultDate: defaultDate,
            selectable: true,
            eventClick: function(calEvent, jsEvent, view) {
                showRemoveEventDialog(calEvent);
            },
            timeFormat: 'HH:mm',
            minTime: '07:00:00',
            maxTime: '19:00:00',
            events: [
                // database taken appointments
                <c:forEach items="${calendar.takenAppointments}" var="pair">
                ${pair.schedule.jsonEventFormattedTaken},
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
    <h1>Calendario</h1>
    <div id="dialog-confirm" title="Cancelar hora" style="display: none;">
        <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span id="modal_message">&nbsp;</span></p>
    </div>
    <div id="calendar_container"></div>
</div>
