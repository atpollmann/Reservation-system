package cl.usach.ingesoft.agendator.facade;

import cl.usach.ingesoft.agendator.entity.UserEntity;

import java.util.List;

public interface IUserAdministration {
    void createUser(UserEntity newUser);

    List<UserEntity> findAllUsers();

    UserEntity findById(int idUser);

    UserEntity findByUsername(String username);

    void deleteUser(int idUser);

    void updateUser(UserEntity user);

    void deleteAllUsers();
}
