package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "professional")
public class ProfessionalEntity extends UserEntity {
    @OmitInComparison
    private SpecialityEntity speciality;

    @OmitInComparison
    private List<ScheduleEntity> schedules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality", referencedColumnName = "id")
    public SpecialityEntity getSpeciality() {return speciality;}
    public void setSpeciality(SpecialityEntity speciality) {this.speciality = speciality;}

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY)
    public List<ScheduleEntity> getSchedules() {return schedules;}
    public void setSchedules(List<ScheduleEntity> schedules) {this.schedules = schedules;}
}
