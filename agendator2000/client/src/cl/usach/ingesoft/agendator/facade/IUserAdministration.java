package cl.usach.ingesoft.agendator.facade;

import cl.usach.ingesoft.agendator.entity.UserEntity;

import java.util.List;

public interface IUserAdministration {
    UserEntity createUser(String username, String password);

    List<UserEntity> findAllUsers();

    UserEntity findById(int idUser);

    UserEntity findByUsername(String username);

    void deleteUser(int idUser);

    UserEntity updateUser(int idUser, String username, String password);

    void deleteAllUsers();
}
