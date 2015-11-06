package cl.usach.ingesoft.agendator.util.dao.impl;

import cl.usach.ingesoft.agendator.util.dao.IContactTypeDAO;
import cl.usach.ingesoft.agendator.entity.ContactTypeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ContactTypeDAO extends BaseDAO<ContactTypeEntity, Integer> implements IContactTypeDAO {
    public ContactTypeDAO() {
        super(ContactTypeEntity.class);
    }
}
