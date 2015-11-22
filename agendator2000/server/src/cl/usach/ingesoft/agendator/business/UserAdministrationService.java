package cl.usach.ingesoft.agendator.business;

import cl.usach.ingesoft.agendator.business.dao.IUserDAO;
import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import cl.usach.ingesoft.agendator.facade.IUserAdministration;
import cl.usach.ingesoft.agendator.view.util.ForTesting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserAdministrationService implements IUserAdministration {

    @Resource private IUserDAO userDAO;

    @ForTesting
    public UserAdministrationService() {
    }

    @ForTesting
    public UserAdministrationService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserEntity createUser(String username, String password) {
        Validator.shouldNotBeEmpty(username);
        Validator.shouldNotBeEmpty(password);

        UserEntity user = userDAO.findByUsername(username);

        Validator.shouldBeNull(user);

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(password);

        userDAO.save(newUser);
        userDAO.flush();

        return newUser;
    }

    @Override
    @Transactional
    public List<UserEntity> findAllUsers() {
        return userDAO.findAll();
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
    public UserEntity updateUser(int idUser, String username, String password) {
        UserEntity user = userDAO.findById(idUser);

        Validator.shouldBeFound(user);

        user.setUsername(username);
        user.setPassword(password);

        userDAO.update(user);
        userDAO.flush();

        return user;
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userDAO.deleteAll();
        userDAO.flush();
    }
}
