package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "careSession")
public class CareSessionEntity extends BaseEntity {
    @OmitInComparison
    private Integer id;
    private OngEntity ong;
    private String location;
    private Date startDate;
    private Date endDate;

    @OmitInComparison
    private List<ScheduleEntity> schedules;

    @Column(name = "id", nullable = false, insertable = true)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ong", referencedColumnName = "id")
    public OngEntity getOng() { return ong;}
    public void setOng(OngEntity ong) {this.ong = ong;}

    @Column(name = "location", nullable = false, insertable = true, updatable = true, length = 255)
    @Basic
    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    @Column(name = "startDate", nullable = false, unique = false, insertable = true, updatable = true)
    @Basic
    public Date getStartDate() {return startDate;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}

    @Column(name = "endDate", nullable = false, unique = false, insertable = true, updatable = true)
    @Basic
    public Date getEndDate() {return endDate;}
    public void setEndDate(Date endDate) {this.endDate = endDate;}

    @OneToMany(mappedBy = "careSession", fetch = FetchType.LAZY)
    public List<ScheduleEntity> getSchedules() {return schedules;}
    public void setSchedules(List<ScheduleEntity> schedules) {this.schedules = schedules;}
}
