package cl.usach.ingesoft.agendator.util.dao;

import cl.usach.ingesoft.agendator.entity.UserEntity;

public interface IUserDAO extends IBaseDAO<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
