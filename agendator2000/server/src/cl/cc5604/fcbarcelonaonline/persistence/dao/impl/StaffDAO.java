package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IStaffDAO;
import cl.cc5604.fcbarcelonaonline.entity.StaffEntity;
import org.springframework.stereotype.Repository;

@Repository
public class StaffDAO extends BaseDAO<StaffEntity, Integer> implements IStaffDAO {

    public StaffDAO() {
        super(StaffEntity.class);
    }

    @Override
    public StaffEntity findByContract(int idContract) {
        return findOneByStatement("select a from StaffEntity a join a.contract c where c.id = :idContract",
            "idContract", idContract
        );
    }
}
