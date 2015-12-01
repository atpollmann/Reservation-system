package cl.usach.ingesoft.agendator.business.bo;

import cl.usach.ingesoft.agendator.entity.*;

import java.util.List;

public class ProfessionalCalendarBO {

    public static class Pair<K, S> {
        public Pair() {}
        public Pair(K schedule, S appointment) {
            this.schedule = schedule;
            this.appointment = appointment;
        }
        public K schedule;
        public S appointment;
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


}
