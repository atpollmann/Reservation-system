package cl.cc5604.fcbarcelonaonline.entity;

import cl.cc5604.fcbarcelonaonline.entity.base.BaseEntity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 27-04-13
 * Time: 07:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "NACIONALIDAD")//, schema = "FCBARCELONA_SCHEMA", catalog = ""
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
