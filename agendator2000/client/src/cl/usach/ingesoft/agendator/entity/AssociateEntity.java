package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.PersonEntity;
import cl.usach.ingesoft.agendator.util.FormatHelper;
import cl.usach.ingesoft.agendator.util.OmitInComparison;
import cl.usach.ingesoft.agendator.util.OmitInHashcode;
import cl.usach.ingesoft.agendator.util.OmitInToString;

import javax.persistence.*;

@Table(name = "SOCIO")
@Entity
public class AssociateEntity extends PersonEntity {

    @OmitInComparison
    private Integer id;

    private boolean seatRight;

    @OmitInComparison
    @OmitInToString
    @OmitInHashcode
    private ContractEntity contract;

    @OmitInComparison
    @OmitInToString
    @OmitInHashcode
    private NationalityEntity nationality;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "DERECHO_ASIENTO", nullable = false, insertable = true, updatable = true, length = 19, precision = 2)
    @Basic
    public boolean getSeatRight() {return seatRight;}
    public void setSeatRight(boolean seatRight) {this.seatRight = seatRight;}

    @ManyToOne
    @JoinColumn(name = "CONTRATO_ID", referencedColumnName = "ID", nullable = false)
    public ContractEntity getContract() {return contract;}
    public void setContract(ContractEntity contract) {this.contract = contract;}

    @ManyToOne
    @JoinColumn(name = "NACIONALIDAD_ID", referencedColumnName = "ID")
    public NationalityEntity getNationality() {return nationality;}
    public void setNationality(NationalityEntity nationality) {this.nationality = nationality;}

    // ---------------- formatters ----------------

    @Transient public String getFormattedSeatRight(){return (seatRight ? "Sí" : "No");}
    @Transient public String getFormattedBirthDate(){return FormatHelper.formatDate(getBirthDate());}
}
