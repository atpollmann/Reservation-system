package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IPassiveDAO;
import cl.cc5604.fcbarcelonaonline.entity.PassiveEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PassiveDAO extends BaseDAO<PassiveEntity, Integer> implements IPassiveDAO {
    public PassiveDAO() {
        super(PassiveEntity.class);
    }
}
