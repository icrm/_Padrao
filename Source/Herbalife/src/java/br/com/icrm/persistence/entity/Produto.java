package br.com.icrm.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HL_PRODUTO")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1982309231785467L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_PRODUTO")
    private Integer cdProduto;
    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "CD_CATEGORIA")
    private Categoria categoria;
    @Column(name = "NM_PRODUTO")
    private String nmProduto;
    @Column(name = "DS_PRODUTO_CURTA")
    private String dsProdutoCurta;
    @Column(name = "DS_PRODUTO_LONGA")
    @Lob
    private byte[] dsProdutoLonga;
    @Column(name = "VL_PRODUTO")
    private BigDecimal vlProduto;
    @ManyToOne(targetEntity = ProdutoStatus.class)
    @JoinColumn(name = "STATUS")
    private ProdutoStatus status;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @OneToMany(mappedBy = "produto", targetEntity = ProdutoImagem.class)
    private List<ProdutoImagem> imagens = new ArrayList<ProdutoImagem>();

    public Produto() {
    }

    public Produto(Integer cdProduto) {
        this.cdProduto = cdProduto;
    }

    public Produto(Integer cdProduto, Categoria categoria, String nmProduto, BigDecimal vlProduto, ProdutoStatus status, Date created) {
        this.cdProduto = cdProduto;
        this.categoria = categoria;
        this.nmProduto = nmProduto;
        this.vlProduto = vlProduto;
        this.status = status;
        this.created = created;
    }

    public Integer getCdProduto() {
        return cdProduto;
    }

    public void setCdProduto(Integer cdProduto) {
        this.cdProduto = cdProduto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNmProduto() {
        return nmProduto;
    }

    public void setNmProduto(String nmProduto) {
        this.nmProduto = nmProduto;
    }

    public String getDsProdutoCurta() {
        return dsProdutoCurta;
    }

    public void setDsProdutoCurta(String dsProdutoCurta) {
        this.dsProdutoCurta = dsProdutoCurta;
    }

    public byte[] getDsProdutoLonga() {
        return dsProdutoLonga;
    }

    public void setDsProdutoLonga(byte[] dsProdutoLonga) {
        this.dsProdutoLonga = dsProdutoLonga;
    }

    public BigDecimal getVlProduto() {
        return vlProduto;
    }

    public void setVlProduto(BigDecimal vlProduto) {
        this.vlProduto = vlProduto;
    }

    public ProdutoStatus getStatus() {
        return status;
    }

    public void setStatus(ProdutoStatus status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<ProdutoImagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<ProdutoImagem> imagens) {
        this.imagens = imagens;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdProduto != null ? cdProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof Produto))) {
            isEqual = false;
        }
        final Produto other = (Produto) object;
        if (isEqual && ((this.cdProduto == null && other.cdProduto != null)
                || (this.cdProduto != null
                && !this.cdProduto.equals(other.cdProduto)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return this.nmProduto;
    }

}
