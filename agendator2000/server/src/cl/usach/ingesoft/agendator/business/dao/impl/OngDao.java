package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IOngDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.OngEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class OngDao extends BaseDao<OngEntity, Integer> implements IOngDao {
    public OngDao() {
        super(OngEntity.class);
    }
}
