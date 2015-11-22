package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDAO;
import cl.usach.ingesoft.agendator.entity.UserEntity;

public interface IUserDAO extends IBaseDAO<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
