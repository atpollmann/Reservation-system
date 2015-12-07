package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.dao.*;
import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.entity.AdministratorEntity;
import cl.usach.ingesoft.agendator.entity.PatientEntity;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
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
    public <T extends UserEntity> T createUser(T user) {
        // hack to circumvent identities not being available for table-per-class implementation on BD
        // Generate id and save for Administrator
        if (AdministratorEntity.class.isAssignableFrom(user.getClass())) {
            administratorDao.save((AdministratorEntity)user);
            administratorDao.flush();
            return user;
        }
        // Generate id and save for Professional
        else if (ProfessionalEntity.class.isAssignableFrom(user.getClass())) {
            professionalDao.save((ProfessionalEntity)user);
            professionalDao.flush();
        }
        // Generate id and save for Patient
        else if (PatientEntity.class.isAssignableFrom(user.getClass())) {
            patientDao.save((PatientEntity)user);
            patientDao.flush();
        } else {
            // guess new id for creating a new user!
            throw new RuntimeException("Unsupported subclass for UserEntity:" + user.getClass().getName());
        }
        return user;
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
        userDao.flush();
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
