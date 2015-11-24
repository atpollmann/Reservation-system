package cl.usach.ingesoft.agendator.business.service;

import cl.usach.ingesoft.agendator.entity.UserEntity;

public interface IUsersService {

    UserEntity createUser(UserEntity user);

    UserEntity findUser(Integer userId);
}
