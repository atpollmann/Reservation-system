package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IAppointmentDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AppointmentDao extends BaseDao<AppointmentEntity, Integer> implements IAppointmentDao {
    public AppointmentDao() {
        super(AppointmentEntity.class);
    }
}
