package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.dao.impl.AppointmentDao;
import cl.usach.ingesoft.agendator.business.service.IAppointmentsService;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentsService implements IAppointmentsService {
    @Autowired private AppointmentDao appointmentDao;

    @Transactional
    @Override
    public AppointmentEntity bookAppointment(AppointmentEntity appointment) {
        appointmentDao.save(appointment);
        appointmentDao.flush();
        return appointment;
    }

    @Transactional
    @Override
    public boolean cancelAppointment(int idUser, int idAppointment) {
        // only the provided idUser can delete its appointments
        AppointmentEntity ae = appointmentDao.findById(idAppointment);
        if (ae.getPatient().getId() != idUser) {
            return false;
        }
        if (ae == null) {
            return false;
        }
        appointmentDao.delete(ae);
        appointmentDao.flush();
        return true;
    }

    @Transactional
    @Override
    public AppointmentEntity updateAppointment(AppointmentEntity appointment) {
        appointmentDao.update(appointment);
        appointmentDao.flush();
        return appointment;
    }

    @Transactional
    @Override
    public List<AppointmentEntity> findByProfessional(int professionalIdUser) {
        return appointmentDao.findByProfessional(professionalIdUser);
    }

    @Transactional
    @Override
    public List<AppointmentEntity> findByAdministrator() {
        return appointmentDao.findAll();
    }

    @Transactional
    @Override
    public List<AppointmentEntity> findByPatient(int patientIdUser) {
        return appointmentDao.findByPatient(patientIdUser);
    }
}
