package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.service.IAppointmentsService;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentsService implements IAppointmentsService {

    @Transactional
    @Override
    public AppointmentEntity bookAppointment(AppointmentEntity appointment) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Transactional
    @Override
    public boolean cancelAppointment(AppointmentEntity appointment) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Transactional
    @Override
    public AppointmentEntity updateAppointment(AppointmentEntity appointment) {
        throw new RuntimeException("Not yet implemented.");
    }
}
