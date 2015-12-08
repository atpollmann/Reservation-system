package cl.usach.ingesoft.agendator.business.bo;

import cl.usach.ingesoft.agendator.entity.*;

import java.io.Serializable;
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
