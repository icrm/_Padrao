package br.com.icrm.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HL_PRODUTO_STATUS")
public class ProdutoStatus implements Serializable {

    private static final long serialVersionUID = 6574839201438290L;
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
    @OneToMany(mappedBy = "status", targetEntity = Produto.class)
    private List<Produto> produtos = new ArrayList<Produto>();

    public ProdutoStatus() {
    }

    public ProdutoStatus(Integer cdStatus) {
        this.cdStatus = cdStatus;
    }

    public ProdutoStatus(Integer cdStatus, String nmStatus, Date created) {
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdStatus != null ? cdStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof ProdutoStatus))) {
            isEqual = false;
        }
        final ProdutoStatus other = (ProdutoStatus) object;
        if (isEqual && ((this.cdStatus == null && other.cdStatus != null)
                || (this.cdStatus != null
                && !this.cdStatus.equals(other.cdStatus)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "br.com.icrm.persistence.entity.HlProdutoStatus[ cdStatus=" + cdStatus + " ]";
    }
}
