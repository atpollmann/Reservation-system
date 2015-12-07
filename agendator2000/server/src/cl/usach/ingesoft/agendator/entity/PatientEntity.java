package cl.usach.ingesoft.agendator.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "patient")
public class PatientEntity extends UserEntity {
    @Override
    public boolean userIsPatient() {return true;}
}
