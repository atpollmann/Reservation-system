package cl.usach.ingesoft.agendator.entity.base;

import javax.persistence.*;

@Table(name = "sequence_entity_tbl")
@Entity
public class SequenceEntity {
    private Integer id;

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="course")
    @TableGenerator(
            name = "course",
            table = "sequence_generator_tbl",
            pkColumnName = "gen_id",
            valueColumnName = "next_id",
            pkColumnValue = "course",
            allocationSize = 30
    )
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
}
