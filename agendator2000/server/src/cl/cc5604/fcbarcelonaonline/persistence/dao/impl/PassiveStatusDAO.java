package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IPassiveStatusDAO;
import cl.cc5604.fcbarcelonaonline.entity.PassiveStatusEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PassiveStatusDAO extends BaseDAO<PassiveStatusEntity, Integer> implements IPassiveStatusDAO {

    public PassiveStatusDAO() {
        super(PassiveStatusEntity.class);
    }

    @Override
    public PassiveStatusEntity findByStatus(String status) {
        return findOneByStatement("from PassiveStatusEntity p where p.status = :status",
                "status", status
        );
    }
}
