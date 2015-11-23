package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IScheduleDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao extends BaseDao<ScheduleEntity, Integer> implements IScheduleDao {
    public ScheduleDao() {
        super(ScheduleEntity.class);
    }
}
