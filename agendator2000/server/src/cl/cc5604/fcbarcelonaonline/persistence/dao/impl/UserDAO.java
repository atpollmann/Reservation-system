package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IUserDAO;
import cl.cc5604.fcbarcelonaonline.entity.UserEntity;
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
