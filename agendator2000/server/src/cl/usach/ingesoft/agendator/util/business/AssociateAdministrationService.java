package cl.usach.ingesoft.agendator.util.business;

import cl.usach.ingesoft.agendator.util.ForTesting;
import cl.usach.ingesoft.agendator.util.business.validator.Validator;
import cl.usach.ingesoft.agendator.facade.IAssociateAdministration;
import cl.usach.ingesoft.agendator.util.dao.*;
import cl.usach.ingesoft.agendator.entity.AssociateEntity;
import cl.usach.ingesoft.agendator.entity.ContactDataEntity;
import cl.usach.ingesoft.agendator.entity.ContactTypeEntity;
import cl.usach.ingesoft.agendator.entity.NationalityEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AssociateAdministrationService implements IAssociateAdministration {

    @Resource private IContractDAO contractDAO;
    @Resource private IAssociateDAO associateDAO;
    @Resource private INationalityDAO nationalityDAO;
    @Resource private IContactDataDAO contactDataDAO;
    @Resource private IContactTypeDAO contactTypeDAO;

    @ForTesting
    public AssociateAdministrationService(IContractDAO contractDAO, IAssociateDAO associateDAO,
            INationalityDAO nationalityDAO, IContactDataDAO contactDataDAO, IContactTypeDAO contactTypeDAO) {
        this.contractDAO = contractDAO;
        this.associateDAO = associateDAO;
        this.nationalityDAO = nationalityDAO;
        this.contactDataDAO = contactDataDAO;
        this.contactTypeDAO = contactTypeDAO;
    }

    @ForTesting
    public AssociateAdministrationService() {
    }

    @Override
    @Transactional
    public void createAssociate(final AssociateEntity associate) {
        Validator.shouldNotBeNull(associate);
        Validator.shouldBeNull(associate.getId());
        Validator.shouldNotBeEmpty(associate.getFirstName());
        Validator.shouldNotBeEmpty(associate.getLastName());
        Validator.shouldNotBeNull(associate.getBirthDate());
        Validator.shouldNotBeNull(associate.getContract());
        Validator.shouldNotBeNull(associate.getNationality());

        // validacion estrictamente necesaria para crear relacion Associate-Contract y Associate-Nationality
        Validator.shouldNotBeNull(associate.getContract().getId());
        Validator.shouldNotBeNull(associate.getNationality().getId());

        // validacion para asegurar relaciones
        Integer contractId = associate.getContract().getId();
        Integer nationalityId = associate.getNationality().getId();

        Validator.shouldNotBeNull(contractId);
        Validator.shouldNotBeNull(nationalityId);

        // verificar que no se intente asociar a un contrato, que ha sido asociado previamente
        Validator.shouldNotBeFound(associateDAO.findByContract(contractId));

        Validator.shouldBeFound(contractDAO.findById(associate.getContract().getId()));
        Validator.shouldBeFound(nationalityDAO.findById(associate.getNationality().getId()));

        associateDAO.save(associate);
        associateDAO.flush();
    }

    @Override
    @Transactional
    public List<AssociateEntity> findAllAssociates() {
        return associateDAO.findAll();
    }

    @Override
    @Transactional
    public AssociateEntity findById(final int id) {
        return associateDAO.findById(id);
    }

    @Override
    @Transactional
    public void updateAssociate(final AssociateEntity associate) {
        Validator.shouldNotBeNull(associate);
        Validator.shouldNotBeNull(associate.getId());
        Validator.shouldNotBeEmpty(associate.getFirstName());
        Validator.shouldNotBeEmpty(associate.getLastName());
        Validator.shouldNotBeNull(associate.getBirthDate());
        Validator.shouldNotBeNull(associate.getContract());
        Validator.shouldNotBeNull(associate.getNationality());

        Integer contractId = associate.getContract().getId();
        Integer nationalityId = associate.getNationality().getId();

        // validacion estrictamente necesaria para crear relacion Associate-Contract y Associate-Nationality
        Validator.shouldNotBeNull(contractId);
        Validator.shouldNotBeNull(nationalityId);

        Validator.shouldBeFound(contractDAO.findById(contractId));
        Validator.shouldBeFound(nationalityDAO.findById(nationalityId));

        AssociateEntity entity = associateDAO.findById(associate.getId());
        Validator.shouldBeFound(entity);
        associateDAO.update(entity);
        associateDAO.flush();

    }

    @Override
    @Transactional
    public void deleteAssociate(final int associateId) {
        AssociateEntity entity = associateDAO.findById(associateId);

        Validator.shouldBeFound(entity);

        // borrar datos de contacto
        for (ContactDataEntity cde : contactDataDAO.findContactDataByAssociate(entity.getId())) {
            contactDataDAO.delete(cde);
        }
        contactDataDAO.flush();

        // borrar socio
        associateDAO.delete(entity);
        associateDAO.flush();

        // borrar contrato
        contractDAO.delete(entity.getContract());
        contractDAO.flush();
    }

    @Override
    @Transactional
    public AssociateEntity findAssociateByContract(int idContract) {
        return associateDAO.findByContract(idContract);
    }

    @Override
    @Transactional
    public List<NationalityEntity> findAllNationalities() {
        return nationalityDAO.findAll();
    }

    @Override
    @Transactional
    public void createContactData(final ContactDataEntity contactData) {
        Validator.shouldNotBeNull(contactData);
        Validator.shouldBeNull(contactData.getId());
        Validator.shouldNotBeNull(contactData.getValueData());
        Validator.shouldNotBeNull(contactData.getAssociate());
        Validator.shouldNotBeNull(contactData.getContactType());

        Integer associateId = contactData.getAssociate().getId();
        Integer typeId = contactData.getContactType().getId();

        // validacion estrictamente necesaria para crear relacion ContactDataEntity-Associate y ContactDataEntity-ContactTypeEntity
        Validator.shouldNotBeNull(associateId);
        Validator.shouldNotBeNull(typeId);

        Validator.shouldBeFound(associateDAO.findById(associateId));
        Validator.shouldBeFound(contactTypeDAO.findById(typeId));

        contactDataDAO.save(contactData);
        contactDataDAO.flush();
    }

    @Override
    @Transactional
    public void deleteContactData(final int idContactData) {
        ContactDataEntity data = contactDataDAO.findById(idContactData);
        Validator.shouldBeFound(data);
        contactDataDAO.delete(data);
    }

    @Override
    @Transactional
    public void updateContactData(final ContactDataEntity contactData) {

        Validator.shouldNotBeNull(contactData);
        Validator.shouldNotBeNull(contactData.getId());
        Validator.shouldNotBeNull(contactData.getValueData());
        Validator.shouldNotBeNull(contactData.getAssociate());
        Validator.shouldNotBeNull(contactData.getContactType());

        // validacion estrictamente necesaria para crear relacion ContactDataEntity-Associate y ContactDataEntity-ContactTypeEntity
        Integer associateId = contactData.getAssociate().getId();
        Integer typeId = contactData.getContactType().getId();

        Validator.shouldNotBeNull(associateId);
        Validator.shouldNotBeNull(typeId);

        Validator.shouldBeFound(associateDAO.findById(associateId));
        Validator.shouldBeFound(contactTypeDAO.findById(typeId));

        ContactDataEntity data = contactDataDAO.findById(contactData.getId());
        Validator.shouldBeFound(data);

        contactDataDAO.update(contactData);
        contactDataDAO.flush();
    }

    @Override
    @Transactional
    public List<ContactTypeEntity> findAllContactTypes() {
        return contactTypeDAO.findAll();
    }

    @Override
    @Transactional
    public List<ContactDataEntity> findAllContactDataByAssociate(final int idAssociate) {
        return contactDataDAO.findContactDataByAssociate(idAssociate);
    }

    @Override
    @Transactional
    public NationalityEntity findNationalityById(int idNationality) {
        return nationalityDAO.findById(idNationality);
    }

    @Override
    @Transactional
    public ContactDataEntity findContactDataById(int id) {
        return contactDataDAO.findById(id);
    }

    @Override
    @Transactional
    public ContactTypeEntity findContactTypeById(int id) {
        return contactTypeDAO.findById(id);
    }
}
