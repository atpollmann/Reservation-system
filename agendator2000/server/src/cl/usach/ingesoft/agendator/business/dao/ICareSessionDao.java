package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;

import java.util.Date;
import java.util.List;

public interface ICareSessionDao extends IBaseDao<CareSessionEntity, Integer> {
    CareSessionEntity findByDate(int idOng, Date currentTime);

    List<CareSessionEntity> findByOng(int ongId);

    List<CareSessionEntity> findPending(int ongId, Date currentDate);
}
