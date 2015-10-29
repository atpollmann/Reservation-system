package cl.cc5604.fcbarcelonaonline.facade;

import cl.cc5604.fcbarcelonaonline.entity.UserEntity;

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
