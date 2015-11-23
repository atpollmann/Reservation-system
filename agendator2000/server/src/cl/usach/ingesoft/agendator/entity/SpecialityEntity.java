package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "speciality")
public class SpecialityEntity extends BaseEntity {
    @OmitInComparison
    private Integer id;
    private String name;

    @OmitInComparison
    private List<ProfessionalEntity> professionals;

    @Column(name = "id", nullable = false, insertable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 40)
    @Basic
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    @OneToMany(mappedBy = "speciality", fetch = FetchType.LAZY)
    public List<ProfessionalEntity> getProfessionals() {return professionals;}
    public void setProfessionals(List<ProfessionalEntity> professionals) {this.professionals = professionals;}
}
