package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;

@Table(name = "TIPO_CONTACTO")
@Entity
public class ContactTypeEntity extends BaseEntity {

    @OmitInComparison
    private Integer id;

    private String description;
    private String type;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 2)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "DESCRIPCION", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    @Column(name = "TIPO", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
}
