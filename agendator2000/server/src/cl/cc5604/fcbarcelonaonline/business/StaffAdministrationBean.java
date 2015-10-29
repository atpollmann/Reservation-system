package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffTypeEntity;
import cl.cc5604.fcbarcelonaonline.facade.IStaffAdministration;
import cl.cc5604.fcbarcelonaonline.persistence.dao.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StaffAdministrationBean implements IStaffAdministration {

    @Resource private IContractDAO contractDAO;
    @Resource private IStaffDAO staffDAO;
    @Resource private IStaffTypeDAO staffTypeDAO;
    @Resource private INationalityDAO nationalityDAO;
    @Resource private IAssociateDAO associateDAO;

    @Override
    @Transactional
    public void createStaff(final StaffEntity staff) {
        Validator.shouldNotBeNull(staff);
        Validator.shouldBeNull(staff.getId());
        Validator.shouldNotBeNegative(staff.getBaseValue());

        Validator.shouldNotBeNull(staff.getContract());
        Validator.shouldNotBeNull(staff.getNationality());
        Validator.shouldNotBeNull(staff.getStaffType());

        // validacion para asegurar relaciones

        Integer contractId = staff.getContract().getId();
        Integer nationalityId = staff.getNationality().getId();
        Integer typeId = staff.getStaffType().getId();

        Validator.shouldNotBeNull(contractId);
        Validator.shouldNotBeNull(nationalityId);
        Validator.shouldNotBeNull(typeId);

        // verificar que no se intente asociar a un contrato, que ha sido asociado previamente
        Validator.shouldNotBeFound(staffDAO.findByContract(contractId));
        Validator.shouldNotBeFound(associateDAO.findByContract(contractId));

        Validator.shouldBeFound(contractDAO.findById(contractId));
        Validator.shouldBeFound(nationalityDAO.findById(nationalityId));
        Validator.shouldBeFound(staffTypeDAO.findById(typeId));

        staffDAO.save(staff);
        staffDAO.flush();

    }

    @Override
    @Transactional
    public List<StaffEntity> findAllStaffs() {
        return staffDAO.findAll();

    }

    @Override
    @Transactional
    public StaffEntity findById(final int id) {
        return staffDAO.findById(id);
    }

    @Override
    @Transactional
    public void updateStaff(final StaffEntity staff) {
        Validator.shouldNotBeNull(staff);
        Validator.shouldNotBeNull(staff.getId());

        Validator.shouldNotBeNull(staff.getContract());
        Validator.shouldNotBeNull(staff.getNationality());
        Validator.shouldNotBeNull(staff.getStaffType());

        // asegurar que estan las FK
        Integer contractId = staff.getContract().getId();
        Integer nationalityId = staff.getNationality().getId();
        Integer typeId = staff.getStaffType().getId();

        Validator.shouldNotBeNull(contractId);
        Validator.shouldNotBeNull(nationalityId);
        Validator.shouldNotBeNull(typeId);

        Validator.shouldBeFound(contractDAO.findById(contractId));
        Validator.shouldBeFound(nationalityDAO.findById(nationalityId));
        Validator.shouldBeFound(staffTypeDAO.findById(typeId));

        StaffEntity entity = staffDAO.findById(staff.getId());
        Validator.shouldBeFound(entity);
        staffDAO.update(staff);
        staffDAO.flush();
    }

    @Override
    @Transactional
    public void deleteStaff(final int staffId) {
        StaffEntity entity = staffDAO.findById(staffId);
        Validator.shouldBeFound(entity);

        ContractEntity contract = entity.getContract();

        staffDAO.delete(entity);
        staffDAO.flush();

        if(contract != null){
            contractDAO.delete(contract);
            contractDAO.flush();
        }
    }

    @Override
    @Transactional
    public List<StaffTypeEntity> findAllStaffTypes() {
        return staffTypeDAO.findAll();
    }

    @Override
    @Transactional
    public List<NationalityEntity> findAllNationalities() {
        return nationalityDAO.findAll();
    }

    @Override
    @Transactional
    public NationalityEntity findNationalityById(int idNationality) {
        return nationalityDAO.findById(idNationality);
    }

    @Override
    @Transactional
    public StaffTypeEntity findStaffTypeById(int idStaffType) {
        return staffTypeDAO.findById(idStaffType);
    }

}
