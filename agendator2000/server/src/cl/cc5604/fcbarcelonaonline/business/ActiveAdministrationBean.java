package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.entity.ActiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.ActiveTypeEntity;
import cl.cc5604.fcbarcelonaonline.facade.IActiveAdministration;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IActiveDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IActiveTypeDAO;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Repository
public class ActiveAdministrationBean implements IActiveAdministration {

    @Resource private IActiveDAO activeDAO;
    @Resource private IActiveTypeDAO activeTypeDAO;

    @Override
    @Transactional
    public void createActive(final ActiveEntity active) {
        Validator.shouldNotBeNull(active);
        Validator.shouldNotBeNull(active.getActiveType());
        Validator.shouldBeNull(active.getId());

        Integer typeId = active.getActiveType().getId();

        Validator.shouldNotBeNull(typeId);
        Validator.shouldBeFound(activeTypeDAO.findById(typeId));

        activeDAO.save(active);
        activeDAO.flush();
    }

    @Override
    @Transactional
    public List<ActiveEntity> findAllActives() {
        return activeDAO.findAll();
    }

    @Override
    @Transactional
    public ActiveEntity findById(final int idActive) {
        return activeDAO.findById(idActive);
    }

    @Override
    @Transactional
    public void updateActive(final ActiveEntity active) {
        Validator.shouldNotBeNull(active);
        Validator.shouldNotBeNull(active.getId());
        Validator.shouldNotBeNull(active.getActiveType());
        Validator.shouldNotBeNegative(active.getValue());

        ActiveEntity found = activeDAO.findById(active.getId());
        Validator.shouldBeFound(found);
        activeDAO.update(active);
        activeDAO.flush();
    }

    @Override
    @Transactional
    public void deleteActive(final int idActive) {
        ActiveEntity found = activeDAO.findById(idActive);
        Validator.shouldBeFound(found);
        activeDAO.delete(found);
        activeDAO.flush();

    }

    @Override
    @Transactional
    public List<ActiveTypeEntity> findAllActiveTypes() {
        return activeTypeDAO.findAll();
    }
}
