package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IPatientDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.PatientEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDao extends BaseDao<PatientEntity, Integer> implements IPatientDao {
    public PatientDao() {
        super(PatientEntity.class);
    }
}
