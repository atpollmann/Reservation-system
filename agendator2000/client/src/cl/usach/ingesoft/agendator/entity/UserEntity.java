package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;

@Table(name = "USUARIO")
@Entity
public class UserEntity  extends BaseEntity {

    @OmitInComparison
    private Integer id;

    private String password;
    private String username;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "PASSWORD", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    @Column(name = "NOMBRE_USUARIO", nullable = false, unique = true, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

}
