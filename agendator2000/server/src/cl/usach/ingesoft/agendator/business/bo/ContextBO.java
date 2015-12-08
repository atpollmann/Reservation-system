package cl.usach.ingesoft.agendator.business.bo;

import cl.usach.ingesoft.agendator.entity.UserEntity;

import java.io.Serializable;

public class ContextBO implements Serializable {

    private UserEntity currentUser;

    public void setCurrentUser(UserEntity currentUser) {this.currentUser = currentUser;}
    public UserEntity getCurrentUser() {return currentUser;}
}
