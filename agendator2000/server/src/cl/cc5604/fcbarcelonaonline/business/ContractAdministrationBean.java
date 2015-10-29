package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.entity.AssociateEntity;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffEntity;
import cl.cc5604.fcbarcelonaonline.facade.IContractAdministration;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IAssociateDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IContractDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IStaffDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContractAdministrationBean implements IContractAdministration {

    @Resource private IContractDAO contractDAO;
    @Resource private IAssociateDAO associateDAO;
    @Resource private IStaffDAO staffDAO;

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

        // borrar personal, si existe asociado
        StaffEntity staff = staffDAO.findByContract(idContract);
        if (staff != null) {
            staffDAO.delete(staff);
            staffDAO.flush();
        }

        // borrar socio, si existe asociado
        AssociateEntity associate = associateDAO.findByContract(idContract);
        if (associate != null) {
            associateDAO.delete(associate);
            associateDAO.flush();
        }

        // borrar contrato
        contractDAO.delete(contract);
        contractDAO.flush();
    }
}
