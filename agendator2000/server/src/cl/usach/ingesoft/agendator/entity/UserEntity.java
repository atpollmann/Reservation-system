package cl.usach.ingesoft.agendator.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity  extends PersonEntity {
    private String email;
    private String hashedPassword;

    @Column(name = "email", nullable = false, unique = true, insertable = true, updatable = true, length = 100)
    @Basic
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    @Column(name = "hashedPassword", nullable = false, insertable = true, updatable = true, length = 40)
    @Basic
    public String getHashedPassword() {return hashedPassword;}
    public void setHashedPassword(String hashedPassword) {this.hashedPassword = hashedPassword;}

    public boolean userIsAdministrator() {return false;}
    public boolean userIsProfessional() {return false;}
    public boolean userIsPatient() {return false;}
}
