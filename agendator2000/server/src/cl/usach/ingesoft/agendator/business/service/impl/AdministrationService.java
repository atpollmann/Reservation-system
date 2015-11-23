package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.service.IAdministrationService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import cl.usach.ingesoft.agendator.util.ForTesting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdministrationService implements IAdministrationService {

    @Resource private IUserDAO userDAO;

    @ForTesting
    public AdministrationService() {
    }

    @ForTesting
    public AdministrationService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserEntity createUser(String email, String password) {
        Validator.shouldNotBeEmpty(email);
        Validator.shouldNotBeEmpty(password);

        UserEntity user = userDAO.findByEmail(email);

        Validator.shouldBeNull(user);

        UserEntity newUser = new UserEntity();
        newUser.setEmail(email);
        newUser.setHashedPassword(password);

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
    public UserEntity findByEmail(String email) {
        Validator.shouldNotBeEmpty(email);
        return userDAO.findByEmail(email);
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
    public UserEntity updateUser(int idUser, String email, String password) {
        UserEntity user = userDAO.findById(idUser);

        Validator.shouldBeFound(user);

        user.setEmail(email);
        user.setHashedPassword(password);

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
