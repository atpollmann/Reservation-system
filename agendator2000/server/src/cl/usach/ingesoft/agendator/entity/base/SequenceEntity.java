package cl.usach.ingesoft.agendator.entity.base;

import javax.persistence.*;

@Table(name = "sequence_entity_temp")
@Entity
public class SequenceEntity {
    private Integer id;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
}
