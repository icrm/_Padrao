package br.com.icrm.persistence.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompraItemPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CD_COMPRA")
    private int cdCompra;
    @Basic(optional = false)
    @Column(name = "CD_PRODUTO")
    private int cdProduto;

    public CompraItemPK() {
    }

    public CompraItemPK(int cdCompra, int cdProduto) {
        this.cdCompra = cdCompra;
        this.cdProduto = cdProduto;
    }

    public int getCdCompra() {
        return cdCompra;
    }

    public void setCdCompra(int cdCompra) {
        this.cdCompra = cdCompra;
    }

    public int getCdProduto() {
        return cdProduto;
    }

    public void setCdProduto(int cdProduto) {
        this.cdProduto = cdProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cdCompra;
        hash += (int) cdProduto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraItemPK)) {
            return false;
        }
        CompraItemPK other = (CompraItemPK) object;
        if (this.cdCompra != other.cdCompra) {
            return false;
        }
        if (this.cdProduto != other.cdProduto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.icrm.persistence.entity.HlCompraItemPK[ cdCompra=" + cdCompra + ", cdProduto=" + cdProduto + " ]";
    }
    
}
