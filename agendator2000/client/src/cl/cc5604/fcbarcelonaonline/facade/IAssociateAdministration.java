package cl.cc5604.fcbarcelonaonline.facade;

import cl.cc5604.fcbarcelonaonline.entity.AssociateEntity;
import cl.cc5604.fcbarcelonaonline.entity.ContactDataEntity;
import cl.cc5604.fcbarcelonaonline.entity.ContactTypeEntity;
import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAssociateAdministration {

    void createAssociate(AssociateEntity associate);

    List<AssociateEntity> findAllAssociates();

    AssociateEntity findById(int idAssociate);

    void updateAssociate(AssociateEntity associate);

    void deleteAssociate(int idAssociate);

    AssociateEntity findAssociateByContract(int idContract);

    // nationality

    List<NationalityEntity> findAllNationalities();

    // contact data

    void createContactData(ContactDataEntity contactData);

    void deleteContactData(int idContactData);

    void updateContactData(ContactDataEntity contactData);

    List<ContactDataEntity> findAllContactDataByAssociate(int idAssociate);

    // contact types

    List<ContactTypeEntity> findAllContactTypes();

    NationalityEntity findNationalityById(int idNationality);

    ContactDataEntity findContactDataById(int i);

    ContactTypeEntity findContactTypeById(int i);
}
