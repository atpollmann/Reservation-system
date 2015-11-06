package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;

import javax.persistence.*;

@Table(name = "NACIONALIDAD")
@Entity
public class NationalityEntity extends BaseEntity {

    private Integer id;
    private String country;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "PAIS", unique = true, nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

}
