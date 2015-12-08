package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.util.OmitInComparison;
import cl.usach.ingesoft.agendator.util.OmitInToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "professional")
public class ProfessionalEntity extends UserEntity {
    @OmitInComparison
    private SpecialityEntity speciality;

    @ManyToOne
    @JoinColumn(name = "speciality", referencedColumnName = "id")
    public SpecialityEntity getSpeciality() {return speciality;}
    public void setSpeciality(SpecialityEntity speciality) {this.speciality = speciality;}

    @Transient
    @Override
    public boolean getIsProfessional() {return true;}

    @Transient
    @Override
    public List<GrantedAuthority> getAuthoritiesByUserType() {
        return makeArray(new SimpleGrantedAuthority("ROLE_PROFESSIONAL"));
    }

    @Transient
    @Override
    public String getRoleName() {
        return "Profesional";
    }
}
