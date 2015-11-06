package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.OmitInComparison;
import cl.usach.ingesoft.agendator.util.OmitInHashcode;
import cl.usach.ingesoft.agendator.util.OmitInToString;

import javax.persistence.*;

@Table(name = "DATO_CONTACTO")
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
