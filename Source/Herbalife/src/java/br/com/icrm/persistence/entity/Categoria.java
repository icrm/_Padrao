package br.com.icrm.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "HL_CATEGORIA")
public class Categoria implements Serializable {
    private static final long serialVersionUID = 3452712237801276L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_CATEGORIA")
    private Integer cdCategoria;
    @ManyToOne(targetEntity = Categoria.class, fetch = FetchType.EAGER)
    @JoinColumn(name =  "CD_PAI")
    private Categoria pai;
    @OneToMany(mappedBy = "pai", targetEntity = Categoria.class, fetch = FetchType.EAGER)
    private List<Categoria> filhos = new ArrayList<Categoria>();
    @Column(name = "NM_CATEGORIA")
    @Length(max = 60, message = "O Nome da Categoria não deve conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome da Categoria não pode estar VAZIO.")
    private String nmCategoria;
    @Column(name = "DS_CATEGORIA")
    @Length(max = 200, message = "A Descrição da Categoria não deve conter mais do que 200 caracteres.")
    private String dsCategoria;
    @ManyToOne(targetEntity = CategoriaStatus.class)
    @JoinColumn(name = "STATUS")
    private CategoriaStatus status;
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @OneToMany(mappedBy = "categoria", targetEntity = Produto.class)
    List<Produto> produtos = new ArrayList<Produto>();

    public Categoria() {
    }

    public Categoria(Integer cdCategoria) {
        this.cdCategoria = cdCategoria;
    }

    public Categoria(Integer cdCategoria, String nmCategoria, CategoriaStatus status, Date created) {
        this.cdCategoria = cdCategoria;
        this.nmCategoria = nmCategoria;
        this.status = status;
        this.created = created;
    }

    public Integer getCdCategoria() {
        return cdCategoria;
    }

    public void setCdCategoria(Integer cdCategoria) {
        this.cdCategoria = cdCategoria;
    }

    public Categoria getPai() {
        return pai;
    }

    public void setPai(Categoria pai) {
        this.pai = pai;
    }

    public String getNmCategoria() {
        return nmCategoria;
    }

    public void setNmCategoria(String nmCategoria) {
        this.nmCategoria = nmCategoria;
    }

    public String getDsCategoria() {
        return dsCategoria;
    }

    public void setDsCategoria(String dsCategoria) {
        this.dsCategoria = dsCategoria;
    }

    public CategoriaStatus getStatus() {
        return status;
    }

    public void setStatus(CategoriaStatus status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Categoria> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<Categoria> filhos) {
        this.filhos = filhos;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        int cdCategoriaHash = 0;
        if (cdCategoria != null) {
            cdCategoriaHash = cdCategoria.hashCode();
        }
        hash += 92 * hash + cdCategoriaHash;
        return hash;
    }

    @Override
    public boolean equals(final Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof Categoria))) {
            isEqual = false;
        }
        final Categoria other = (Categoria) object;
        if (isEqual && ((this.cdCategoria == null && other.cdCategoria != null)
                || (this.cdCategoria != null
                && !this.cdCategoria.equals(other.cdCategoria)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return this.nmCategoria;
    }
    
}
