package cl.usach.ingesoft.agendator.business.service;

import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUsersService {
    /**
     * Operation 1.
     *
     * @param user User to be created (must not come from the database).
     * @return User created, with its generated id already set.
     */
    UserEntity createUser(UserEntity user);

    /**
     *
     * @return Finds the list of all registered users in the application.
     */
    List<UserEntity> findAllUsers();

    /**
     * Operation 2.
     *
     * @param idUser Id for the user to delete.
     * @return Whether the User could be deleted or not from the database (true means deleted, otherwise false).
     */
    boolean deleteUser(int idUser);

    /**
     * Operation 3.
     *
     * @param user User to update (it should come from the database, and it may have its parameters updated).
     * @return The newly updated entity.
     */
    UserEntity updateUser(UserEntity user);

    /**
     * Operation 4.
     *
     * @param idUser Id of the user to find.
     * @return User taken from the database.
     */
    UserEntity findUser(int idUser);

    /**
     *
     * @param email Email for the user to retrieve (case insensitive).
     * @return User found for the given email, or null if none exists.
     */
    UserEntity findUserByEmail(String email);
}
