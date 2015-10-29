package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IActiveTypeDAO;
import cl.cc5604.fcbarcelonaonline.entity.ActiveTypeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveTypeDAO extends BaseDAO<ActiveTypeEntity, Integer> implements IActiveTypeDAO {

    public ActiveTypeDAO() {
        super(ActiveTypeEntity.class);
    }
}
