package cl.usach.ingesoft.agendator.util.business;

import cl.usach.ingesoft.agendator.util.ForTesting;
import cl.usach.ingesoft.agendator.util.business.validator.Validator;
import cl.usach.ingesoft.agendator.entity.AssociateEntity;
import cl.usach.ingesoft.agendator.entity.ContractEntity;
import cl.usach.ingesoft.agendator.facade.IContractAdministration;
import cl.usach.ingesoft.agendator.util.dao.IAssociateDAO;
import cl.usach.ingesoft.agendator.util.dao.IContactDataDAO;
import cl.usach.ingesoft.agendator.util.dao.IContractDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContractAdministrationService implements IContractAdministration {

    @Resource private IContractDAO contractDAO;
    @Resource private IAssociateDAO associateDAO;
    @Resource private IContactDataDAO contactDataDAO;

    @ForTesting
    public ContractAdministrationService() {
    }

    @ForTesting
    public ContractAdministrationService(IContractDAO contractDAO, IAssociateDAO associateDAO,
            IContactDataDAO contactDataDAO) {
        this.contractDAO = contractDAO;
        this.associateDAO = associateDAO;
        this.contactDataDAO = contactDataDAO;
    }

    @Override
    @Transactional
    public void createContract(final ContractEntity newContract) {
        // required
        Validator.shouldNotBeNull(newContract);
        Validator.shouldBeNull(newContract.getId());
        Validator.shouldNotBeNull(newContract.getInitDate());
        Validator.shouldNotBeNull(newContract.getExpirationDate());

        // business logic
        Validator.shouldNotBeNegative(newContract.getMonthlyPayment());
        Validator.shouldBeOrdered(newContract.getInitDate().getTime(), newContract.getExpirationDate().getTime());

        // crear contrato
        contractDAO.save(newContract);
        contractDAO.flush();
    }

    @Override
    @Transactional
    public List<ContractEntity> findAllContracts() {
        return contractDAO.findAll();
    }

    @Override
    @Transactional
    public List<ContractEntity> findAllAvailableContracts() {
        return contractDAO.findAllAvailableContracts();
    }

    @Override
    @Transactional
    public ContractEntity findById(final int id) {
        return contractDAO.findById(id);
    }

    @Override
    @Transactional
    public void updateContract(final ContractEntity contract) {
        // required
        Validator.shouldNotBeNull(contract);
        Validator.shouldNotBeNull(contract.getId());
        Validator.shouldNotBeNull(contract.getInitDate());
        Validator.shouldNotBeNull(contract.getExpirationDate());

        ContractEntity found = contractDAO.findById(contract.getId());
        Validator.shouldBeFound(found);

        // actualizar en base de datos
        contractDAO.update(contract);
        contractDAO.flush();

    }

    @Override
    @Transactional
    public void deleteContract(final int idContract) {
        ContractEntity contract = contractDAO.findById(idContract);
        Validator.shouldBeFound(contract);

        // borrar socio, si existe asociado
        AssociateEntity associate = associateDAO.findByContract(idContract);
        if (associate != null) {
            // borrar datos de contacto, si es que existen
            contactDataDAO.deleteByAssociate(associate.getId());
            contactDataDAO.flush();

            // finalmente borrar asociado
            associateDAO.delete(associate);
            associateDAO.flush();
        }

        // borrar contrato
        contractDAO.delete(contract);
        contractDAO.flush();

    }
}
