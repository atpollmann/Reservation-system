package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.UserEntity;

import java.util.List;

public interface IUserDao extends IBaseDao<UserEntity, Integer> {
    UserEntity findByEmail(String email);

    List<UserEntity> findAllOrderedById();
}
