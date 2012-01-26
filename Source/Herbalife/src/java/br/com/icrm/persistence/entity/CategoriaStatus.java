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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "HL_CATEGORIA_STATUS")
public class CategoriaStatus implements Serializable {

    private static final long serialVersionUID = 7634236178944536L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_STATUS")
    private Integer cdStatus;
    @Column(name = "NM_STATUS")
    @Length(max = 60, message = "O Nome do Status não pode conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome do Status não pode estar VAZIO.")
    private String nmStatus;
    @Column(name = "DS_STATUS")
    @Length(max = 200, message = "A Descrição do Status não pode conter mais do que 200 caracteres.")
    private String dsStatus;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @OneToMany(mappedBy = "status", targetEntity = Categoria.class)
    private List<Categoria> categorias = new ArrayList<Categoria>();

    public CategoriaStatus() {
    }

    public CategoriaStatus(Integer cdStatus) {
        this.cdStatus = cdStatus;
    }

    public CategoriaStatus(Integer cdStatus, String nmStatus, Date created) {
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

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        int cdStatusHash = 0;
        if (cdStatus != null) {
            cdStatusHash = cdStatus.hashCode();
        }
        hash += 76 * hash + cdStatusHash;
        return hash;
    }

    @Override
    public boolean equals(final Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && !(object instanceof CategoriaStatus)) {
            isEqual = false;
        }
        final CategoriaStatus other = (CategoriaStatus) object;
        if (isEqual && ((this.cdStatus == null && other.cdStatus != null)
                || (this.cdStatus != null
                && !this.cdStatus.equals(other.cdStatus)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return this.nmStatus;
    }
}
