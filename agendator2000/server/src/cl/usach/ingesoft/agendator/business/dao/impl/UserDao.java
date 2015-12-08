package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IUserDao;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDao;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends BaseDao<UserEntity, Integer> implements IUserDao {

    public UserDao() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return findOneByStatement("from UserEntity u where u.email = :email",
            "email", email
        );
    }

    @Override
    public List<UserEntity> findAllOrderedById() {
        return findByStatement("from UserEntity u order by u.id");
    }
}