package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IContactDataDAO;
import cl.cc5604.fcbarcelonaonline.entity.ContactDataEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDataDAO extends BaseDAO<ContactDataEntity, Integer> implements IContactDataDAO {

    public ContactDataDAO() {
        super(ContactDataEntity.class);
    }

    @Override
    public List<ContactDataEntity> findContactDataByAssociate(int idAssociate) {
        String statement = "from ContactDataEntity c where c.associate.id = :id ";
        return findByStatement(statement,
                "id", idAssociate
        );
    }
}
