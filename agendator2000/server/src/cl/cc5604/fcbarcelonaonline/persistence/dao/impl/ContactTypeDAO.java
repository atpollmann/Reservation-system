package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IContactTypeDAO;
import cl.cc5604.fcbarcelonaonline.entity.ContactTypeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ContactTypeDAO extends BaseDAO<ContactTypeEntity, Integer> implements IContactTypeDAO {

    public ContactTypeDAO() {
        super(ContactTypeEntity.class);
    }
}
