package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patient")
public class PatientEntity extends UserEntity {
    @OmitInComparison
    private List<AppointmentEntity> appointments;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    public List<AppointmentEntity> getAppointments() {return appointments;}
    public void setAppointments(List<AppointmentEntity> appointments) {this.appointments = appointments;}
}
