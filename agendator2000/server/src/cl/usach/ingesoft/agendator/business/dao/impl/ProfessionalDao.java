package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IProfessionalDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProfessionalDao extends BaseDao<ProfessionalEntity, Integer> implements IProfessionalDao {
    public ProfessionalDao() {
        super(ProfessionalEntity.class);
    }
}
