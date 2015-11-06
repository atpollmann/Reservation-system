package cl.usach.ingesoft.agendator.util.dao;

import cl.usach.ingesoft.agendator.entity.AssociateEntity;

public interface IAssociateDAO extends IBaseDAO<AssociateEntity, Integer> {
    AssociateEntity findByContract(int idContract);
}
