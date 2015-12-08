package cl.usach.ingesoft.agendator.entity.base;

import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;

@MappedSuperclass
public class PersonEntity extends BaseEntity {
    @OmitInComparison
    private Integer id;
    private Integer run;
    private String firstName;
    private String lastName;

    @Column(name = "id", nullable = false, insertable = true)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "run", nullable = false, insertable = true, updatable = true)
    @Basic
    public Integer getRun() {return run;}
    public void setRun(Integer run) {this.run = run;}

    @Column(name = "firstName", nullable = false, insertable = true, updatable = true, length = 50)
    @Basic
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    @Column(name = "lastName", nullable = false, insertable = true, updatable = true, length = 50)
    @Basic
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    @Transient
    public char getDv() {
        return Validator.getDv(String.valueOf(run));
    }
}
