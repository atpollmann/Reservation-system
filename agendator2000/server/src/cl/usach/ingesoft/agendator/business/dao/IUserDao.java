package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.UserEntity;

public interface IUserDao extends IBaseDao<UserEntity, Integer> {
    UserEntity findByEmail(String email);
}
