package cl.cc5604.fcbarcelonaonline.entity;

import cl.cc5604.fcbarcelonaonline.entity.base.BaseEntity;
import cl.cc5604.fcbarcelonaonline.util.FormatHelper;
import cl.cc5604.fcbarcelonaonline.util.OmitInComparison;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 27-04-13
 * Time: 07:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "CONTRATO")//, schema = "FCBARCELONA_SCHEMA", catalog = ""
@Entity
public class ContractEntity extends BaseEntity {

    @OmitInComparison
    private Integer id;

    private Date expirationDate;
    private Date initDate;
    private int monthlyPayment;

    // indica si este entity esta libre para ser asociado (hooked2associate=false)
    @OmitInComparison
    private boolean hooked2associate;

    public void setHooked2associate(boolean hooked2associate) {
        this.hooked2associate = hooked2associate;
    }

    @Formula("(SELECT COUNT(*)>0 FROM SOCIO S WHERE S.CONTRATO_ID = id)")
    public boolean getHooked2associate() {
        return hooked2associate;
    }

    // indica si este entity esta libre para ser asociado (hooked2staff=false)
    @OmitInComparison
    private boolean hooked2staff;

    public void setHooked2staff(boolean hooked2staff) {
        this.hooked2staff = hooked2staff;
    }

    @Formula("(SELECT COUNT(*)>0 FROM PERSONAL P WHERE P.CONTRATO_ID = id)")
    public boolean getHooked2staff() {
        return hooked2staff;
    }

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "FECHA_EXPIRACION", nullable = false, insertable = true, updatable = true, length = 11, precision = 6)
    @Temporal(TemporalType.DATE)
    @Basic
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "FECHA_INICIO", nullable = false, insertable = true, updatable = true, length = 11, precision = 6)
    @Temporal(TemporalType.DATE)
    @Basic
    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    @Column(name = "MENSUALIDAD", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(int monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    // ---------------- formatters ----------------


    @Transient
    public String getFormattedInitDate() {
        return FormatHelper.formatDate(initDate);
    }

    @Transient
    public String getFormattedExpirationDate() {
        return FormatHelper.formatDate(expirationDate);
    }

    @Transient
    public String getFormattedMonthlyPayment() {
        return FormatHelper.formatCurrency(monthlyPayment);
    }

    @Transient public String getFormattedHooked2staff(){return (hooked2staff ? "Sí" : "No");}
    @Transient public String getFormattedHooked2associate(){return (hooked2associate? "Sí" : "No");}
}
