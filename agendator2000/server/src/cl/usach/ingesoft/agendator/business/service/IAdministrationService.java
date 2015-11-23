package cl.usach.ingesoft.agendator.business.service;

import cl.usach.ingesoft.agendator.entity.UserEntity;

import java.util.List;

public interface IAdministrationService {
    UserEntity createUser(String username, String password);

    List<UserEntity> findAllUsers();

    UserEntity findById(int idUser);

    UserEntity findByEmail(String email);

    void deleteUser(int idUser);

    UserEntity updateUser(int idUser, String email, String hashedPassword);

    void deleteAllUsers();
}
