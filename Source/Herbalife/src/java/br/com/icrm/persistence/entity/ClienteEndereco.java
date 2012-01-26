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
@Table(name = "HL_CLIENTE_ENDERECO")
public class ClienteEndereco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_ENDERECO")
    private Integer cdEndereco;
    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "CD_CLIENTE")
    private Cliente cliente;
    @Column(name = "NM_ENDERECO")
    private String nmEndereco;
    @Column(name = "DS_ENDERECO")
    private String dsEndereco;
    @Column(name = "NU_ENDERECO")
    private Integer nuEndereco;
    @Column(name = "DS_COMPLEMENTO")
    private String dsComplemento;
    @Column(name = "DS_BAIRRO")
    private String dsBairro;
    @Column(name = "DS_CIDADE")
    private String dsCidade;
    @Column(name = "NU_CEP")
    private String nuCep;
    @Column(name = "SG_UF")
    private String sgUf;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public ClienteEndereco() {
    }

    public ClienteEndereco(Integer cdEndereco) {
        this.cdEndereco = cdEndereco;
    }

    public ClienteEndereco(Integer cdEndereco, Cliente cliente, String nmEndereco, String dsEndereco, Integer nuEndereco, String dsBairro, String dsCidade, String nuCep, String sgUf, Date created) {
        this.cdEndereco = cdEndereco;
        this.cliente = cliente;
        this.nmEndereco = nmEndereco;
        this.dsEndereco = dsEndereco;
        this.nuEndereco = nuEndereco;
        this.dsBairro = dsBairro;
        this.dsCidade = dsCidade;
        this.nuCep = nuCep;
        this.sgUf = sgUf;
        this.created = created;
    }

    public Integer getCdEndereco() {
        return cdEndereco;
    }

    public void setCdEndereco(Integer cdEndereco) {
        this.cdEndereco = cdEndereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNmEndereco() {
        return nmEndereco;
    }

    public void setNmEndereco(String nmEndereco) {
        this.nmEndereco = nmEndereco;
    }

    public String getDsEndereco() {
        return dsEndereco;
    }

    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    public Integer getNuEndereco() {
        return nuEndereco;
    }

    public void setNuEndereco(Integer nuEndereco) {
        this.nuEndereco = nuEndereco;
    }

    public String getDsComplemento() {
        return dsComplemento;
    }

    public void setDsComplemento(String dsComplemento) {
        this.dsComplemento = dsComplemento;
    }

    public String getDsBairro() {
        return dsBairro;
    }

    public void setDsBairro(String dsBairro) {
        this.dsBairro = dsBairro;
    }

    public String getDsCidade() {
        return dsCidade;
    }

    public void setDsCidade(String dsCidade) {
        this.dsCidade = dsCidade;
    }

    public String getNuCep() {
        return nuCep;
    }

    public void setNuCep(String nuCep) {
        this.nuCep = nuCep;
    }

    public String getSgUf() {
        return sgUf;
    }

    public void setSgUf(String sgUf) {
        this.sgUf = sgUf;
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
        hash += (cdEndereco != null ? cdEndereco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof ClienteEndereco))) {
            isEqual = false;
        }
        final ClienteEndereco other = (ClienteEndereco) object;
        if (isEqual && (this.cdEndereco != other.cdEndereco
                && (this.cdEndereco == null
                || !this.cdEndereco.equals(other.cdEndereco)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return nmEndereco;
    }
    
}
