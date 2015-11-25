package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;

import java.util.Date;

public interface ICareSessionDao extends IBaseDao<CareSessionEntity, Integer> {
    CareSessionEntity findByDate(int idOng, Date currentTime);
}
