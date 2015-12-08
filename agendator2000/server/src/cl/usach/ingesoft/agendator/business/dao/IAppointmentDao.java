package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;

import java.util.List;

public interface IAppointmentDao extends IBaseDao<AppointmentEntity, Integer> {
    List<AppointmentEntity> findByProfessionalByCareSession(int idProfessional, int idCareSession);
    List<AppointmentEntity> findByProfessional(int idProfessional);
    List<AppointmentEntity> findByPatient(int idPatient);
}
