package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IAppointmentDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentDao extends BaseDao<AppointmentEntity, Integer> implements IAppointmentDao {
    public AppointmentDao() {
        super(AppointmentEntity.class);
    }

    @Override
    public List<AppointmentEntity> findByProfessionalByCareSession(int idProfessional, int idCareSession) {
        return findByStatement(
                "from AppointmentEntity a where a.schedule.professional.id = :idProfessional " +
                        "and a.schedule.careSession.id = :idCareSession",
                "idProfessional", idProfessional,
                "idCareSession", idCareSession
        );
    }

    @Override
    public List<AppointmentEntity> findByProfessional(int idProfessional) {
        return findByStatement("from AppointmentEntity a where a.schedule.professional.id = :id",
                "id", idProfessional
        );
    }

    @Override
    public List<AppointmentEntity> findByPatient(int idPatient) {
        return findByStatement("from AppointmentEntity a where a.patient.id = :id",
                "id", idPatient
        );
    }
}
