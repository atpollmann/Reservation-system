package cl.usach.ingesoft.agendator.util.dao.impl;

import cl.usach.ingesoft.agendator.util.dao.IContactDataDAO;
import cl.usach.ingesoft.agendator.entity.ContactDataEntity;
import org.hibernate.Query;
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

    @Override
    public void deleteByAssociate(int idAssociate) {
        String statement = "delete from ContactDataEntity c where c.associate.id = :id";
        Query query = session()
                .createQuery(statement)
                .setInteger("id", idAssociate);
        query.executeUpdate();
    }
}
