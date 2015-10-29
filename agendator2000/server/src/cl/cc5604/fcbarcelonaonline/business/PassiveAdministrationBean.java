package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.facade.IPassiveAdministration;
import cl.cc5604.fcbarcelonaonline.persistence.dao.impl.PassiveDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.impl.PassiveStatusDAO;
import cl.cc5604.fcbarcelonaonline.entity.PassiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.PassiveStatusEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PassiveAdministrationBean implements IPassiveAdministration {

    private @Resource PassiveDAO passiveDAO;
    private @Resource PassiveStatusDAO passiveStatusDAO;

    @Override
    @Transactional
    public void createPassive(PassiveEntity passive) {
        // required
        Validator.shouldNotBeNull(passive);
        Validator.shouldBeNull(passive.getId());
        Validator.shouldNotBeNull(passive.getPassiveStatus());

        // validacion estrictamente necesaria para asegurar la relacion PassiveEntity-PassiveStatusEntity
        Integer passiveStatusId = passive.getPassiveStatus().getId();
        Validator.shouldNotBeNull(passiveStatusId);
        Validator.shouldBeFound(passiveStatusDAO.findById(passiveStatusId));

        passiveDAO.save(passive);
        passiveDAO.flush();
    }

    @Override
    @Transactional
    public List<PassiveEntity> findAllPassives() {
        return passiveDAO.findAll();
    }

    @Override
    @Transactional
    public PassiveEntity findById(final int idPassive) {
        return passiveDAO.findById(idPassive);
    }

    @Override
    @Transactional
    public void updatePassive(PassiveEntity passive) {
        Validator.shouldNotBeNull(passive);
        Validator.shouldNotBeNull(passive.getId());
        Validator.shouldNotBeNull(passive.getPassiveStatus());

        // validacion necesaria para garantizar la relacion PassiveEntity-PassiveStatusEntity
        Integer passiveStatusId = passive.getPassiveStatus().getId();
        Validator.shouldNotBeNull(passiveStatusId);
        Validator.shouldBeFound(passiveStatusDAO.findById(passiveStatusId));

        PassiveEntity found = passiveDAO.findById(passive.getId());
        Validator.shouldBeFound(found);

        passiveDAO.update(passive);
        passiveDAO.flush();
    }

    @Override
    @Transactional
    public void deletePassive(final int idPassive) {
        PassiveEntity passive = passiveDAO.findById(idPassive);
        Validator.shouldBeFound(passive);
        passiveDAO.delete(passive);
        passiveDAO.flush();
    }

    @Override
    @Transactional
    public List<PassiveStatusEntity> findAllPassiveStatus() {
        return passiveStatusDAO.findAll();
    }

    @Override
    @Transactional
    public PassiveStatusEntity findPassiveStatusByStatus(String status) {
        return passiveStatusDAO.findByStatus(status);
    }

}
