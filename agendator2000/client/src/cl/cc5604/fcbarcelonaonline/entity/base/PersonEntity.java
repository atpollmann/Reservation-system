package cl.cc5604.fcbarcelonaonline.entity.base;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 27-04-13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class PersonEntity extends BaseEntity {

    private String lastName;
    private Date birthDate;
    private String firstName;

    @Column(name = "APELLIDO", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    @Column(name = "FECHA_NACIMIENTO", nullable = false, insertable = true, updatable = true, length = 11, precision = 6)
    @Temporal(TemporalType.DATE)
    @Basic
    public Date getBirthDate() {return birthDate;}
    public void setBirthDate(Date birthDate) {this.birthDate = birthDate;}

    @Column(name = "NOMBRE", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

}
