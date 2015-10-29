package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.INationalityDAO;
import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import org.springframework.stereotype.Repository;

@Repository
public class NationalityDAO extends BaseDAO<NationalityEntity, Integer> implements INationalityDAO {

    public NationalityDAO() {
        super(NationalityEntity.class);
    }

    @Override
    public NationalityEntity findByName(String name) {
        return findOneByStatement("from NationalityEntity n where n.country=:country",
                "country", name
        );
    }
}
