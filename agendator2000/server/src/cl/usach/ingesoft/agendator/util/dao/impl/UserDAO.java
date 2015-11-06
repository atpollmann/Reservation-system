package cl.usach.ingesoft.agendator.util.dao.impl;

import cl.usach.ingesoft.agendator.util.dao.IUserDAO;
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
