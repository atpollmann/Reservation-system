package cl.usach.ingesoft.agendator.util.dao.impl;

import cl.usach.ingesoft.agendator.util.dao.IAssociateDAO;
import cl.usach.ingesoft.agendator.entity.AssociateEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AssociateDAO extends BaseDAO<AssociateEntity, Integer> implements IAssociateDAO {
    public AssociateDAO() {
        super(AssociateEntity.class);
    }

    @Override
    public AssociateEntity findByContract(int idContract) {
        return findOneByStatement("select a from AssociateEntity a join a.contract c where c.id = :idContract",
            "idContract", idContract
        );
    }
}
