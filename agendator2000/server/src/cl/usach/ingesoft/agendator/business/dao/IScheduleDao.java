package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;

import java.util.List;

public interface IScheduleDao extends IBaseDao<ScheduleEntity, Integer> {
    /**
     * Saves every element of a list of schedules.
     * @param schedules Schedules to be saved (with Professional and CareSession already set).
     */
    void saveAll(List<ScheduleEntity> schedules);

    List<ScheduleEntity> findByProfessionalByCareSession(int idProfessional, int idCareSession);
}
