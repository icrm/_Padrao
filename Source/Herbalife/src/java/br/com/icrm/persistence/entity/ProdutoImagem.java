package br.com.icrm.persistence.entity;

import java.io.Serializable;
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
@Table(name = "HL_PRODUTO_IMAGEM")
public class ProdutoImagem implements Serializable {
    private static final long serialVersionUID = 32375489012345L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_IMAGEM")
    private Integer cdImagem;
    @ManyToOne(targetEntity = Produto.class)
    @JoinColumn(name = "CD_PRODUTO")
    private Produto produto;
    @Column(name = "NM_IMAGEM")
    private String nmImagem;
    @Column(name = "DS_PATH")
    private String dsPath;
    @Column(name = "DS_CONTENT")
    private byte[] dsContent;
    @Column(name = "DS_TYPE")
    private String dsType;
    @Column(name = "NU_SIZE")
    private Long nuSize;
    @Column(name = "NU_WIDTH")
    private Long nuWidth;
    @Column(name = "NU_HEIGHT")
    private Long nuHeight;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public ProdutoImagem() {
    }

    public ProdutoImagem(Integer cdImagem) {
        this.cdImagem = cdImagem;
    }

    public ProdutoImagem(Integer cdImagem, Produto produto, String nmImagem, String dsPath, Date created) {
        this.cdImagem = cdImagem;
        this.produto = produto;
        this.nmImagem = nmImagem;
        this.dsPath = dsPath;
        this.created = created;
    }

    public Integer getCdImagem() {
        return cdImagem;
    }

    public void setCdImagem(Integer cdImagem) {
        this.cdImagem = cdImagem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getNmImagem() {
        return nmImagem;
    }

    public void setNmImagem(String nmImagem) {
        this.nmImagem = nmImagem;
    }

    public String getDsPath() {
        return dsPath;
    }

    public void setDsPath(String dsPath) {
        this.dsPath = dsPath;
    }

    public byte[] getDsContent() {
        return dsContent;
    }

    public void setDsContent(byte[] dsContent) {
        this.dsContent = dsContent;
    }

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public Long getNuSize() {
        return nuSize;
    }

    public void setNuSize(Long nuSize) {
        this.nuSize = nuSize;
    }

    public Long getNuWidth() {
        return nuWidth;
    }

    public void setNuWidth(Long nuWidth) {
        this.nuWidth = nuWidth;
    }

    public Long getNuHeight() {
        return nuHeight;
    }

    public void setNuHeight(Long nuHeight) {
        this.nuHeight = nuHeight;
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
        hash += (cdImagem != null ? cdImagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof ProdutoImagem))) {
            isEqual = false;
        }
        final ProdutoImagem other = (ProdutoImagem) object;
        if (isEqual && ((this.cdImagem == null && other.cdImagem != null)
                || (this.cdImagem != null
                && !this.cdImagem.equals(other.cdImagem)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "br.com.icrm.persistence.entity.HlProdutoImagem[ cdImagem=" + cdImagem + " ]";
    }
    
}
