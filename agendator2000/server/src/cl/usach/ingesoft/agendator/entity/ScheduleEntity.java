package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;
import cl.usach.ingesoft.agendator.util.OmitInToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedule")
public class ScheduleEntity extends BaseEntity {
    @OmitInComparison
    private Integer id;
    private ProfessionalEntity professional;
    private CareSessionEntity careSession;
    private Date startDate;
    private Date endDate;

    @Column(name = "id", nullable = false, insertable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @ManyToOne
    @JoinColumn(name = "professional", referencedColumnName = "id")
    public ProfessionalEntity getProfessional() {return professional;}
    public void setProfessional(ProfessionalEntity professional) {this.professional = professional;}

    @ManyToOne
    @JoinColumn(name = "careSession", referencedColumnName = "id")
    public CareSessionEntity getCareSession() {return careSession;}
    public void setCareSession(CareSessionEntity careSession) {this.careSession = careSession;}

    @Column(name = "startDate", nullable = false, insertable = true, updatable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    @Basic
    public Date getStartDate() {return startDate;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}

    @Column(name = "endDate", nullable = false, insertable = true, updatable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    @Basic
    public Date getEndDate() {return endDate;}
    public void setEndDate(Date endDate) {this.endDate = endDate;}
}
