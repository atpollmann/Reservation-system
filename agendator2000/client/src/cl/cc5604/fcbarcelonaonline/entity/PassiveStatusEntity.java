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
@Table(name = "ESTADO_PASIVO")//, schema = "FCBARCELONA_SCHEMA", catalog = ""
@Entity
public class PassiveStatusEntity extends BaseEntity {

    private Integer id;
    private String description;
    private String status;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 2)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "DESCRIPCION", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    @Column(name = "ESTADO", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}
