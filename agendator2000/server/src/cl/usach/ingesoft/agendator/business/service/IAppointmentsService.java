package cl.usach.ingesoft.agendator.business.service;


import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;

import java.util.Date;
import java.util.List;

public interface IAppointmentsService {
    /**
     * Operation 8.
     *
     * @param appointment Apointment to be scheduled.
     * @return Appointment scheduled.
     */
    AppointmentEntity bookAppointment(AppointmentEntity appointment);

    /**
     * Operation 9.
     *
     * @param idUser
     * @param idAppointment Id of the appointment to be canceled.
     * @return Whether the appoint could be canceled or not (true means canceled, otherwise false).
     */
    boolean cancelAppointment(int idUser, int idAppointment);

    /**
     * Operation 10.
     *
     * @param appointment Appointment to be updated.
     * @return Appointment updated.
     */
    AppointmentEntity updateAppointment(AppointmentEntity appointment);

    List<AppointmentEntity> findByProfessional(int professionalIdUser);
    List<AppointmentEntity> findByAdministrator();
    List<AppointmentEntity> findByPatient(int patientIdUser);
}
