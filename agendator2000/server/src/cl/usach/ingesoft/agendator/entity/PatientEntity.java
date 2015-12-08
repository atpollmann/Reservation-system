package cl.usach.ingesoft.agendator.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "patient")
public class PatientEntity extends UserEntity {
    @Transient
    @Override
    public boolean getIsPatient() {return true;}

    @Transient
    @Override
    public List<GrantedAuthority> getAuthoritiesByUserType() {
        return makeArray(new SimpleGrantedAuthority("ROLE_PATIENT"));
    }

    @Transient
    @Override
    public String getRoleName() {
        return "Paciente";
    }
}
