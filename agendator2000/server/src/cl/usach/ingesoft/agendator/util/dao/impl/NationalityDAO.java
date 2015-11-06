package cl.usach.ingesoft.agendator.util.dao.impl;

import cl.usach.ingesoft.agendator.util.dao.INationalityDAO;
import cl.usach.ingesoft.agendator.entity.NationalityEntity;
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
