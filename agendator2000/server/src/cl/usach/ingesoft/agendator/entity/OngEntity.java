package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;
import cl.usach.ingesoft.agendator.util.OmitInToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ong")
public class OngEntity extends BaseEntity {

    public static final int ONLY_ONE_ONG = 1;

    @OmitInComparison
    private Integer id;
    private String name;

    @Column(name = "id", nullable = false, insertable = true)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 100)
    @Basic
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
