package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.PersonEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity extends PersonEntity {
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

    @Transient public boolean getIsAdministrator() {return false;}
    @Transient public boolean getIsProfessional() {return false;}
    @Transient public boolean getIsPatient() {return false;}

    protected List<GrantedAuthority> makeArray(GrantedAuthority ... ga) {
        return Arrays.asList(ga);
    }

    @Transient
    public List<GrantedAuthority> getAuthoritiesByUserType() {
        throw new IllegalStateException("Can't determine authorities for UserEntity, make sure to call this method on a subclass");
    }

    @Transient
    public String getRoleName() {
        throw new IllegalStateException("Can't get roleName for UserEntity, make sure to call this method on a subclass");
    }
}
