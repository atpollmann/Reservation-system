package cl.cc5604.fcbarcelonaonline.entity;

import cl.cc5604.fcbarcelonaonline.entity.base.BaseEntity;
import cl.cc5604.fcbarcelonaonline.util.FormatHelper;
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
@Table(name = "ACTIVO")//, schema = "FCBARCELONA_SCHEMA", catalog = ""
@Entity
public class ActiveEntity extends BaseEntity {

    @OmitInComparison
    private Integer id;

    private int value;

    @OmitInComparison
    @OmitInHashcode
    @OmitInToString
    private ActiveTypeEntity activeType;

    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    @Column(name = "VALOR", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getValue() {return value;}
    public void setValue(int value) {this.value = value;}

    @ManyToOne
    @JoinColumn(name = "TIPO_ACTIVO_ID", referencedColumnName = "ID", nullable = false)
    public ActiveTypeEntity getActiveType() {return activeType;}
    public void setActiveType(ActiveTypeEntity activeType) {this.activeType = activeType;}

    // ---------------- formatters ----------------
    @Transient public String getFormattedValue(){return FormatHelper.formatCurrency(value);}
}
