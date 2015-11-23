package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.ICareSessionDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CareSessionDao extends BaseDao<CareSessionEntity, Integer> implements ICareSessionDao {
    public CareSessionDao() {
        super(CareSessionEntity.class);
    }
}
