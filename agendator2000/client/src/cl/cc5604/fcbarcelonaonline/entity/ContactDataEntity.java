package cl.cc5604.fcbarcelonaonline.entity;

import cl.cc5604.fcbarcelonaonline.entity.base.BaseEntity;
import cl.cc5604.fcbarcelonaonline.util.OmitInComparison;
import cl.cc5604.fcbarcelonaonline.util.OmitInHashcode;
import cl.cc5604.fcbarcelonaonline.util.OmitInToString;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 27-04-13
 * Time: 07:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "DATO_CONTACTO")//, schema = "FCBARCELONA_SCHEMA", catalog = ""
@Entity
public class ContactDataEntity extends BaseEntity {

    @OmitInComparison
    private Integer id;

    private String valueData;

    @OmitInComparison
    @OmitInHashcode
    @OmitInToString
    private AssociateEntity associate;

    @OmitInComparison
    @OmitInHashcode
    @OmitInToString
    private ContactTypeEntity contactType;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "DATO_VALOR", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getValueData() {return valueData;}
    public void setValueData(String valueData) {this.valueData = valueData;}

    @ManyToOne
    @JoinColumn(name = "SOCIO_ID", referencedColumnName = "ID", nullable = false)
    public AssociateEntity getAssociate() {return associate;}
    public void setAssociate(AssociateEntity associate) {this.associate = associate;}

    @ManyToOne
    @JoinColumn(name = "TIPO_CONTACTO_ID", referencedColumnName = "ID", nullable = false)
    public ContactTypeEntity getContactType() {return contactType;}
    public void setContactType(ContactTypeEntity contactType) {this.contactType = contactType;}
}
