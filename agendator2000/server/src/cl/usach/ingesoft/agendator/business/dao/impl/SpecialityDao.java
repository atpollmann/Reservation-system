package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.ISpecialityDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.SpecialityEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialityDao extends BaseDao<SpecialityEntity, Integer> implements ISpecialityDao {
    public SpecialityDao() {
        super(SpecialityEntity.class);
    }
}
