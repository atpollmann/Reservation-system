package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.util.OmitInComparison;
import cl.usach.ingesoft.agendator.util.OmitInToString;

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
}
