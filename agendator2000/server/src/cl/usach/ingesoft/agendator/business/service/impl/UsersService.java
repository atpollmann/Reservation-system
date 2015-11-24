package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.service.IAdministrationService;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService implements IAdministrationService {
    @Override
    public CareSessionEntity createCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public boolean cancelCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public CareSessionEntity updateCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public OngEntity findCurrentOng(Date currentTime) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public CareSessionEntity findCurrentCareSession(OngEntity ong, Date currentTime) {
        throw new RuntimeException("Not yet implemented.");
    }


/*
    @Override
    @Transactional
    public UserEntity createUser(UserEntity user) {
        Validator.shouldNotBeEmpty(user.getEmail());
        Validator.shouldNotBeEmpty(user.getHashedPassword());
        Validator.shouldNotBeEmpty(user.getFirstName());
        Validator.shouldNotBeEmpty(user.getLastName());

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
    }*/
}
