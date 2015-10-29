package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IActiveDAO;
import cl.cc5604.fcbarcelonaonline.entity.ActiveEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveDAO extends BaseDAO<ActiveEntity, Integer> implements IActiveDAO {

    public ActiveDAO() {
        super(ActiveEntity.class);
    }
}
