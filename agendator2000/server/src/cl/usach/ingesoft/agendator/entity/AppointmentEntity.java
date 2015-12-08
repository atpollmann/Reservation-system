package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;

@Entity
@Table(name = "appointment")
public class AppointmentEntity extends BaseEntity {
    @OmitInComparison
    private Integer id;
    private ScheduleEntity schedule;
    private PatientEntity patient;
    private Boolean attended;

    @Column(name = "id", nullable = false, insertable = true)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @ManyToOne
    @JoinColumn(name = "schedule", referencedColumnName = "id")
    public ScheduleEntity getSchedule() {return schedule;}
    public void setSchedule(ScheduleEntity schedule) {this.schedule = schedule;}

    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id")
    public PatientEntity getPatient() {return patient;}
    public void setPatient(PatientEntity patient) {this.patient = patient;}

    @Column(name = "attended", columnDefinition = "BIT", nullable = false, insertable = true, updatable = true)
    @Basic
    public Boolean getAttended() {return attended;}
    public void setAttended(Boolean attended) {this.attended = attended;}
}
