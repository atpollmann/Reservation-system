package cl.cc5604.fcbarcelonaonline.entity;

import cl.cc5604.fcbarcelonaonline.entity.base.PersonEntity;
import cl.cc5604.fcbarcelonaonline.util.FormatHelper;
import cl.cc5604.fcbarcelonaonline.util.OmitInComparison;
import cl.cc5604.fcbarcelonaonline.util.OmitInHashcode;
import cl.cc5604.fcbarcelonaonline.util.OmitInToString;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 27-04-13
 * Time: 07:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "PERSONAL")//, schema = "FCBARCELONA_SCHEMA", catalog = ""
@Entity
public class StaffEntity extends PersonEntity {

    @OmitInComparison
    private Integer id;

    private boolean hired;
    private Integer baseValue;

    @OmitInComparison
    @OmitInToString
    @OmitInHashcode
    private ContractEntity contract;

    @OmitInComparison
    @OmitInToString
    @OmitInHashcode
    private NationalityEntity nationality;

    @OmitInComparison
    @OmitInToString
    @OmitInHashcode
    private StaffTypeEntity staffType;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "CONTRATADO", nullable = false, insertable = true, updatable = true, length = 19, precision = 2)
    @Basic
    public boolean getHired() {return hired;}
    public void setHired(boolean hired) {this.hired = hired;}

    @Column(name = "VALOR_BASE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getBaseValue() {return baseValue;}
    public void setBaseValue(Integer baseValue) {this.baseValue = baseValue;}

    // ---

    @ManyToOne
    @JoinColumn(name = "CONTRATO_ID", referencedColumnName = "ID")
    public ContractEntity getContract() {return contract;}
    public void setContract(ContractEntity contract) {this.contract = contract;}

    @ManyToOne
    @JoinColumn(name = "NACIONALIDAD_ID", referencedColumnName = "ID", nullable = false)
    public NationalityEntity getNationality() {return nationality;}
    public void setNationality(NationalityEntity nationality) {this.nationality = nationality;}

    @ManyToOne
    @JoinColumn(name = "TIPO_PERSONAL_ID", referencedColumnName = "ID", nullable = false)
    public StaffTypeEntity getStaffType() {return staffType;}
    public void setStaffType(StaffTypeEntity staffType) {this.staffType = staffType;}

    // ---------------- formatters ----------------

    @Transient public String getFormattedHired() {return (hired ? "Sí" : "No");}
    @Transient public String getFormattedBirthDate(){return FormatHelper.formatDate(getBirthDate());}
    @Transient public String getFormattedBaseValue(){return FormatHelper.formatCurrency(baseValue);}
}
