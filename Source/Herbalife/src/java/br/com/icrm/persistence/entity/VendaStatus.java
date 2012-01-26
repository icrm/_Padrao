package br.com.icrm.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HL_VENDA_STATUS")
public class VendaStatus implements Serializable {
    private static final long serialVersionUID = 3427854637298745L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_STATUS")
    private Integer cdStatus;
    @Column(name = "NM_STATUS")
    private String nmStatus;
    @Column(name = "DS_STATUS")
    private String dsStatus;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public VendaStatus() {
    }

    public VendaStatus(Integer cdStatus) {
        this.cdStatus = cdStatus;
    }

    public VendaStatus(Integer cdStatus, String nmStatus, Date created) {
        this.cdStatus = cdStatus;
        this.nmStatus = nmStatus;
        this.created = created;
    }

    public Integer getCdStatus() {
        return cdStatus;
    }

    public void setCdStatus(Integer cdStatus) {
        this.cdStatus = cdStatus;
    }

    public String getNmStatus() {
        return nmStatus;
    }

    public void setNmStatus(String nmStatus) {
        this.nmStatus = nmStatus;
    }

    public String getDsStatus() {
        return dsStatus;
    }

    public void setDsStatus(String dsStatus) {
        this.dsStatus = dsStatus;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdStatus != null ? cdStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof VendaStatus))) {
            isEqual = false;
        }
        final VendaStatus other = (VendaStatus) object;
        if (isEqual && (this.cdStatus != other.cdStatus
                && (this.cdStatus == null
                || !this.cdStatus.equals(other.cdStatus)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return nmStatus;
    }
    
}
