package cl.usach.ingesoft.agendator.entity;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
public class AdministratorEntity extends UserEntity {
    private String type;

    @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 40)
    @Basic
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
}