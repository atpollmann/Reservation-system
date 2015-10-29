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
@Table(name = "TIPO_PERSONAL")//, schema = "FCBARCELONA_SCHEMA", catalog = ""
@Entity
public class StaffTypeEntity extends BaseEntity {

    private Integer id;
    private String description;
    private String type;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "DESCRIPCION", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    @Column(name = "TIPO", unique = true, nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
}
