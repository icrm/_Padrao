package br.com.icrm.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HL_VENDA")
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_COMPRA")
    private Integer cdCompra;
    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "CD_CLIENTE")
    private Cliente cliente;
    @ManyToOne(targetEntity = ClienteEndereco.class)
    @JoinColumn(name = "CD_ENDERECO")
    private ClienteEndereco endereco;
    @Column(name = "QT_ITENS")
    private long qtItens;
    @Column(name = "VL_COMPRA")
    private BigDecimal vlCompra;
    @Column(name = "VL_DESCONTO")
    private BigDecimal vlDesconto;
    @Column(name = "VL_TOTAL")
    private BigDecimal vlTotal;
    @Column(name = "DS_OBSERVACAO")
    private String dsObservacao;
    @ManyToOne(targetEntity = VendaStatus.class)
    @JoinColumn(name = "STATUS")
    private VendaStatus status;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Venda() {
    }

    public Venda(Integer cdCompra) {
        this.cdCompra = cdCompra;
    }

    public Venda(Integer cdCompra, Cliente cliente, ClienteEndereco endereco, long qtItens, BigDecimal vlCompra, BigDecimal vlDesconto, BigDecimal vlTotal, VendaStatus status, Date created) {
        this.cdCompra = cdCompra;
        this.cliente = cliente;
        this.endereco = endereco;
        this.qtItens = qtItens;
        this.vlCompra = vlCompra;
        this.vlDesconto = vlDesconto;
        this.vlTotal = vlTotal;
        this.status = status;
        this.created = created;
    }

    public Integer getCdCompra() {
        return cdCompra;
    }

    public void setCdCompra(Integer cdCompra) {
        this.cdCompra = cdCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ClienteEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(ClienteEndereco endereco) {
        this.endereco = endereco;
    }

    public long getQtItens() {
        return qtItens;
    }

    public void setQtItens(long qtItens) {
        this.qtItens = qtItens;
    }

    public BigDecimal getVlCompra() {
        return vlCompra;
    }

    public void setVlCompra(BigDecimal vlCompra) {
        this.vlCompra = vlCompra;
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

    public String getDsObservacao() {
        return dsObservacao;
    }

    public void setDsObservacao(String dsObservacao) {
        this.dsObservacao = dsObservacao;
    }

    public VendaStatus getStatus() {
        return status;
    }

    public void setStatus(VendaStatus status) {
        this.status = status;
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
        hash += (cdCompra != null ? cdCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof Venda))) {
            isEqual = false;
        }
        final Venda other = (Venda) object;
        if (isEqual && (this.cdCompra != other.cdCompra
                && (this.cdCompra == null
                || !this.cdCompra.equals(other.cdCompra)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "br.com.icrm.persistence.entity.HlCompra[ cdCompra=" + cdCompra + " ]";
    }
    
}
