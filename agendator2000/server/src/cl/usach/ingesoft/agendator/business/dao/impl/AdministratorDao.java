package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IAdministratorDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.AdministratorEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AdministratorDao extends BaseDao<AdministratorEntity, Integer> implements IAdministratorDao {
    public AdministratorDao() {
        super(AdministratorEntity.class);
    }
}
