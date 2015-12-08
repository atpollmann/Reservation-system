package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.ICareSessionDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class CareSessionDao extends BaseDao<CareSessionEntity, Integer> implements ICareSessionDao {
    public CareSessionDao() {
        super(CareSessionEntity.class);
    }

    @Override
    public CareSessionEntity findByDate(int idOng, Date currentDate) {
        return findOneByStatement(
                "from CareSessionEntity c where c.ong.id = :idOng " +
                    "and c.startDate <= :currentDate " +
                    "and :currentDate <= c.endDate",
                "idOng", idOng,
                "currentDate", currentDate
        );
    }

    @Override
    public List<CareSessionEntity> findByOng(int ongId) {
        return findByStatement("from CareSessionEntity c where c.ong.id = :id",
                "id", ongId
        );
    }

    @Override
    public List<CareSessionEntity> findPending(int ongId, Date currentDate) {
        return findByStatement("from CareSessionEntity c where c.ong.id = :id and :date <= c.endDate",
                "id", ongId,
                "date", currentDate
        );
    }
}
