package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.entity.UserEntity;
import cl.cc5604.fcbarcelonaonline.facade.IUserAdministration;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IUserDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserAdministrationBean implements IUserAdministration {

    @Resource private IUserDAO userDAO;

    @Override
    @Transactional
    public List<UserEntity> findAllUsers() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public void createUser(UserEntity newUser) {
        Validator.shouldNotBeNull(newUser);
        Validator.shouldBeNull(newUser.getId());
        Validator.shouldNotBeEmpty(newUser.getUsername());
        Validator.shouldNotBeEmpty(newUser.getPassword());

        userDAO.save(newUser);
        userDAO.flush();
    }

    @Override
    @Transactional
    public UserEntity findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    @Transactional
    public UserEntity findByUsername(String username) {
        Validator.shouldNotBeEmpty(username);

        Validator.shouldNotBeNull(username);
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        UserEntity user = userDAO.findById(id);
        Validator.shouldBeFound(user);
        userDAO.delete(user);
        userDAO.flush();

    }

    @Override
    @Transactional
    public void updateUser(UserEntity user) {
        // required
        Validator.shouldNotBeNull(user);
        Validator.shouldNotBeNull(user.getId());
        Validator.shouldNotBeEmpty(user.getUsername());
        Validator.shouldNotBeEmpty(user.getPassword());

        UserEntity entity = userDAO.findById(user.getId());
        Validator.shouldBeFound(entity);
        userDAO.update(user);
        userDAO.flush();

    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userDAO.deleteAll();
    }
}
