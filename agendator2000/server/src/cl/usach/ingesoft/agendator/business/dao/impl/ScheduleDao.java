package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IScheduleDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
                if (s.getId() == null) {
                    sess.saveOrUpdate(s);
                } else {
                    sess.merge(s);
                }
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

    @Override
    public List<ScheduleEntity> findByProfessional(int idProfessional) {
        return findByStatement(
                "from ScheduleEntity s where s.professional.id = :idProfessional",
                "idProfessional", idProfessional
        );
    }

    @Override
    public void deleteSchedulesWithAppointments(List<Integer> ids) {
        // Delete attached Appointments to mantain referential integrity
        Query query = session().createQuery("delete from AppointmentEntity a where a.schedule.id in (:ids)");
        query.setParameterList("ids", ids);
        query.executeUpdate();

        // Delete schedules
        Query query2 = session().createQuery("delete from ScheduleEntity s where s.id in (:ids)");
        query2.setParameterList("ids", ids);
        query2.executeUpdate();
    }
}
