package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IScheduleDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleDao extends BaseDao<ScheduleEntity, Integer> implements IScheduleDao {
    public ScheduleDao() {
        super(ScheduleEntity.class);
    }

    public void saveAll(List<ScheduleEntity> schedules) {
        Session sess = session();
        for (ScheduleEntity s : schedules) {
            if (s != null) {
                sess.save(s);
            }
        }
    }

    @Override
    public List<ScheduleEntity> findByProfessionalByCareSession(int idProfessional, int idCareSession) {
        return findByStatement(
                "from ScheduleEntity s where s.professional.id = :idProfessional and s.careSession.id = :idCareSession",
                "idProfessional", idProfessional,
                "idCareSession", idCareSession
        );
    }
}
