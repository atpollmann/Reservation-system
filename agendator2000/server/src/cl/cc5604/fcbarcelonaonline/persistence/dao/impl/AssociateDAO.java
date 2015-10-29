package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IAssociateDAO;
import cl.cc5604.fcbarcelonaonline.entity.AssociateEntity;
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
