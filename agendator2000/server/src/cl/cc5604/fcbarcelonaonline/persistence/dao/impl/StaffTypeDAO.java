package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IStaffTypeDAO;
import cl.cc5604.fcbarcelonaonline.entity.StaffTypeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class StaffTypeDAO extends BaseDAO<StaffTypeEntity, Integer> implements IStaffTypeDAO {

    public StaffTypeDAO() {
        super(StaffTypeEntity.class);
    }
}
