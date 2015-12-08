package cl.usach.ingesoft.agendator.business.bo;

import cl.usach.ingesoft.agendator.entity.*;
import cl.usach.ingesoft.agendator.entity.base.DateHelper;
import cl.usach.ingesoft.agendator.util.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ProfessionalCalendarBO implements Serializable{

    public static class Pair<K, S> implements Serializable {
        public Pair() {}
        public Pair(K schedule, S appointment) {
            this.schedule = schedule;
            this.appointment = appointment;
        }
        private K schedule;
        private S appointment;

        public K getSchedule() {
            return schedule;
        }

        public S getAppointment() {
            return appointment;
        }

        @Override
        public String toString() {
            return "Pair=[" + String.valueOf(schedule) + "/" + String.valueOf(appointment) + "]";
        }
    }

    private CareSessionEntity careSession;
    private ProfessionalEntity professional;
    private List<ScheduleEntity> freeSchedules;
    private List<Pair<ScheduleEntity, AppointmentEntity> > takenAppointments;

    private Date minDate;
    private Date maxDate;

    public ProfessionalCalendarBO() {
    }

    public ProfessionalCalendarBO(CareSessionEntity careSession, ProfessionalEntity professional,
            List<ScheduleEntity> freeSchedules, List<Pair<ScheduleEntity, AppointmentEntity> > takenAppointments) {
        this.careSession = careSession;
        this.professional = professional;
        this.freeSchedules = freeSchedules;
        this.takenAppointments = takenAppointments;
    }

    public CareSessionEntity getCareSession() {
        return careSession;
    }

    public ProfessionalEntity getProfessional() {
        return professional;
    }

    public List<ScheduleEntity> getFreeSchedules() {
        return freeSchedules;
    }

    public List<Pair<ScheduleEntity, AppointmentEntity> > getTakenAppointments() {
        return takenAppointments;
    }

    private void maybeCalculateMinMaxDates() {
        if (minDate == null && maxDate == null) {
            if (freeSchedules.size() > 0) {
                // Iterate first over the free schedules, collecting the minDate,maxDate
                Iterator<ScheduleEntity> itr = freeSchedules.iterator();
                if (itr.hasNext()) {
                    ScheduleEntity n = itr.next();
                    minDate = n.getStartDate();
                    maxDate = n.getEndDate();
                }
                while(itr.hasNext()) {
                    ScheduleEntity n = itr.next();
                    minDate = DateUtils.minForDates(minDate, n.getStartDate());
                    maxDate = DateUtils.maxForDates(maxDate, n.getEndDate());
                }
                // Iterate then over the taken appointments
                Iterator<Pair<ScheduleEntity, AppointmentEntity>> itr2 = takenAppointments.iterator();
                if (itr2.hasNext()) {
                    Pair<ScheduleEntity, AppointmentEntity> p = itr2.next();
                    minDate = DateUtils.minForDates(minDate, p.schedule.getStartDate());
                    maxDate = DateUtils.maxForDates(maxDate, p.schedule.getEndDate());
                }
                while(itr2.hasNext()) {
                    Pair<ScheduleEntity, AppointmentEntity> p = itr2.next();
                    minDate = DateUtils.minForDates(minDate, p.schedule.getStartDate());
                    maxDate = DateUtils.maxForDates(maxDate, p.schedule.getEndDate());
                }
            }
        }
    }

    public DateHelper getDates() {
        maybeCalculateMinMaxDates();
        return new DateHelper(minDate, maxDate);
    }

    @Override
    public String toString() {
        return "ProfessionalCalendarBO=[\n" +
            "\t" + String.valueOf(careSession) + "\n" +
            "\t" + String.valueOf(freeSchedules) + "\n" +
            "\t" + String.valueOf(careSession) + "\n" +
            "\t" + String.valueOf(takenAppointments) + "\n" +
        "]";
    }
}
