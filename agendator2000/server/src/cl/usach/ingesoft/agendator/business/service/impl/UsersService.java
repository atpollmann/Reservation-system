package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.dao.IAdministratorDao;
import cl.usach.ingesoft.agendator.business.dao.IPatientDao;
import cl.usach.ingesoft.agendator.business.dao.IProfessionalDao;
import cl.usach.ingesoft.agendator.business.dao.IUserDao;
import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService implements IUsersService {

    @Autowired private IUserDao userDao;

    @Autowired private IAdministratorDao administratorDao;
    @Autowired private IProfessionalDao professionalDao;
    @Autowired private IPatientDao patientDao;

    @Transactional
    @Override
    public UserEntity createUser(UserEntity user) {
        // guess new id for creating a new user!
        throw new RuntimeException("Not yet implemented.");
    }

    @Transactional
    @Override
    public List<UserEntity> findAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public boolean deleteUser(int idUser) {
        UserEntity ue = userDao.findById(idUser);
        if (ue == null) {
            return false;
        }
        userDao.delete(ue);
        userDao.flush();
        return (userDao.findById(idUser) == null);
    }

    @Transactional
    @Override
    public UserEntity updateUser(UserEntity user) {
        userDao.update(user);
        return user;
    }

    @Transactional
    @Override
    public UserEntity findUser(int idUser) {
        return userDao.findById(idUser);
    }

    @Transactional
    @Override
    public UserEntity findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
