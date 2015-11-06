package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.base.BaseEntity;
import cl.usach.ingesoft.agendator.util.FormatHelper;
import cl.usach.ingesoft.agendator.util.OmitInComparison;

import javax.persistence.*;
import java.util.Date;

@Table(name = "CONTRATO")
@Entity
public class ContractEntity extends BaseEntity {

    @OmitInComparison
    private Integer id;

    private Date expirationDate;
    private Date initDate;
    private int monthlyPayment;

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
}
