package cl.usach.ingesoft.agendator.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "administrator")
public class AdministratorEntity extends UserEntity {
    private String type;

    @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 40)
    @Basic
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    @Transient
    @Override
    public boolean getIsAdministrator() {return true;}

    @Transient
    @Override
    public List<GrantedAuthority> getAuthoritiesByUserType() {
        return makeArray(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
    }

    @Transient
    @Override
    public String getRoleName() {
        return "Administrador";
    }
}