package cl.usach.ingesoft.agendator.util.dao;

import cl.usach.ingesoft.agendator.entity.ContactDataEntity;

import java.util.List;

public interface IContactDataDAO extends IBaseDAO<ContactDataEntity, Integer> {
    List<ContactDataEntity> findContactDataByAssociate(int idAssociate);
    void deleteByAssociate(int idAssociate);
}
