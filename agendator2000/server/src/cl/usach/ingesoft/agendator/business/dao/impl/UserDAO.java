package cl.usach.ingesoft.agendator.business.dao.impl;

import cl.usach.ingesoft.agendator.business.dao.IUserDAO;
import cl.usach.ingesoft.agendator.business.dao.base.BaseDAO;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends BaseDAO<UserEntity, Integer> implements IUserDAO {
    public UserDAO() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return findOneByStatement("from UserEntity u where u.username=:name",
                "name", username
        );
    }
}
