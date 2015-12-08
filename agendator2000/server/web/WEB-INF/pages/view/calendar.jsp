<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
    function updateGridHeight() {
        $('#calendar_container div.fc-time-grid-container').css('height', '720px');
    }

    $(document).ready(function() {
        var calendarContainer = "#calendar_container";

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
                    duration: { days: 7}
                    //titleFormat: ' ', //YYYY
                    //buttonText: '7 day',
                    //columnFormat: 'dddd',
                    //hiddenDays: [0, 6] // Hide Sunday and Saturday?
                }
            },
            businessHours:false,
            defaultDate: date_str(moment()),
            selectable: true,
            select: function(start, end, allDay) {
                if(date_str(end) != date_str(start)){
                    $(calendarContainer).fullCalendar('unselect') ;
                    return;
                }
                var start2 = start.clone(); start2.add(15, 'minutes');
                var start3 = start.clone(); start3.add(30, 'minutes');

                var allEvents = $(calendarContainer).fullCalendar('clientEvents');

                var firstEvent = my_find(allEvents, start, function(el) {
                    return start.isSame(el.start);
                });
                var secondEvent = my_find(allEvents, start2, function(el) {
                    return start2.isSame(el.start);
                });

                var startTime = null;
                var endTime = null;
                var title = null;
                if (!firstEvent) {
                    title = '1er bloque';
                    startTime = start;
                    endTime = start2;
                } else if (firstEvent && !secondEvent) {
                    title = '2do bloque';
                    startTime = start2;
                    endTime = start3;
                }

                if (startTime && endTime && title) {
                    addEvent({
                        id: new Date().getTime(),
                        title: title,
                        start: startTime,
                        end: endTime
                    });

                    updateGridHeight();
                }
            },
            eventClick: function(calEvent, jsEvent, view) {
                if (window.confirm("Seguro que quiere borrarlo?")) {
                    $(calendarContainer).fullCalendar('removeEvents', function (event) {
                        return (event == calEvent);
                    });
                    updateGridHeight();
                }
            },
            timeFormat: 'H:mm',
            minTime: '07:00:00',
            maxTime: '19:00:00',
            events: [],
            allDaySlot: false,
            businessHours: {
                start: '8:00', // a start time (10am in this example)
                end: '18:00', // an end time (6pm in this example)
                dow: [1, 2, 3, 4, 5]
            }
        });

        updateGridHeight();
    });
</script>

<div class="rounded_corners">
    <h1>Calendario</h1>
    <div id="calendar_container"></div>
</div>
