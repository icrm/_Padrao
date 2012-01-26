package br.com.icrm.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HL_COMPRA_ITEM")
public class CompraItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompraItemPK hlCompraItemPK;
    @Column(name = "QT_PRODUTO")
    private long qtProduto;
    @Column(name = "VL_UNITARIO")
    private BigDecimal vlUnitario;
    @Column(name = "VL_DESCONTO")
    private BigDecimal vlDesconto;
    @Column(name = "VL_TOTAL")
    private BigDecimal vlTotal;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public CompraItem() {
    }

    public CompraItem(CompraItemPK hlCompraItemPK) {
        this.hlCompraItemPK = hlCompraItemPK;
    }

    public CompraItem(CompraItemPK hlCompraItemPK, long qtProduto, BigDecimal vlUnitario, BigDecimal vlDesconto, BigDecimal vlTotal, Date created) {
        this.hlCompraItemPK = hlCompraItemPK;
        this.qtProduto = qtProduto;
        this.vlUnitario = vlUnitario;
        this.vlDesconto = vlDesconto;
        this.vlTotal = vlTotal;
        this.created = created;
    }

    public CompraItem(int cdCompra, int cdProduto) {
        this.hlCompraItemPK = new CompraItemPK(cdCompra, cdProduto);
    }

    public CompraItemPK getHlCompraItemPK() {
        return hlCompraItemPK;
    }

    public void setHlCompraItemPK(CompraItemPK hlCompraItemPK) {
        this.hlCompraItemPK = hlCompraItemPK;
    }

    public long getQtProduto() {
        return qtProduto;
    }

    public void setQtProduto(long qtProduto) {
        this.qtProduto = qtProduto;
    }

    public BigDecimal getVlUnitario() {
        return vlUnitario;
    }

    public void setVlUnitario(BigDecimal vlUnitario) {
        this.vlUnitario = vlUnitario;
    }

    public BigDecimal getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(BigDecimal vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public BigDecimal getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(BigDecimal vlTotal) {
        this.vlTotal = vlTotal;
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
        hash += (hlCompraItemPK != null ? hlCompraItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof CompraItem))) {
            isEqual = false;
        }
        final CompraItem other = (CompraItem) object;
        if (isEqual && (this.hlCompraItemPK != other.hlCompraItemPK
                && (this.hlCompraItemPK == null
                || !this.hlCompraItemPK.equals(other.hlCompraItemPK)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "br.com.icrm.persistence.entity.HlCompraItem[ hlCompraItemPK=" + hlCompraItemPK + " ]";
    }
    
}
