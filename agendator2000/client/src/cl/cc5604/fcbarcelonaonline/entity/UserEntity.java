package cl.cc5604.fcbarcelonaonline.entity;

import cl.cc5604.fcbarcelonaonline.entity.base.BaseEntity;
import cl.cc5604.fcbarcelonaonline.util.OmitInComparison;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 27-04-13
 * Time: 07:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "USUARIO")//, schema = "FCBARCELONA_SCHEMA", catalog = ""
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
