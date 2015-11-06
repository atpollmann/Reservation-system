package cl.usach.ingesoft.agendator.facade;

import cl.usach.ingesoft.agendator.entity.AssociateEntity;
import cl.usach.ingesoft.agendator.entity.ContactDataEntity;
import cl.usach.ingesoft.agendator.entity.ContactTypeEntity;
import cl.usach.ingesoft.agendator.entity.NationalityEntity;

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
