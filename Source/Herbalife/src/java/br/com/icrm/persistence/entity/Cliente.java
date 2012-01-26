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
@Table(name = "HL_CLIENTE")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_CLIENTE")
    private Integer cdCliente;
    @Column(name = "NM_CLIENTE")
    private String nmCliente;
    @Column(name = "DS_EMAIL")
    private String dsEmail;
    @Column(name = "DS_LOGIN")
    private String dsLogin;
    @Column(name = "DS_SENHA")
    private String dsSenha;
    @Column(name = "NU_DDD_RES")
    private String nuDddRes;
    @Column(name = "NU_DDD_CEL")
    private String nuDddCel;
    @Column(name = "NU_DDD_COM")
    private String nuDddCom;
    @Column(name = "NU_TEL_RES")
    private String nuTelRes;
    @Column(name = "NU_TEL_CEL")
    private String nuTelCel;
    @Column(name = "NU_TEL_COM")
    private String nuTelCom;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @OneToMany(mappedBy = "cliente", targetEntity = ClienteEndereco.class)
    private List<ClienteEndereco> enderecos = new ArrayList<ClienteEndereco>();

    public Cliente() {
    }

    public Cliente(Integer cdCliente) {
        this.cdCliente = cdCliente;
    }

    public Cliente(Integer cdCliente, String nmCliente, String dsEmail, String dsLogin, String dsSenha, Date created) {
        this.cdCliente = cdCliente;
        this.nmCliente = nmCliente;
        this.dsEmail = dsEmail;
        this.dsLogin = dsLogin;
        this.dsSenha = dsSenha;
        this.created = created;
    }

    public Integer getCdCliente() {
        return cdCliente;
    }

    public void setCdCliente(Integer cdCliente) {
        this.cdCliente = cdCliente;
    }

    public String getNmCliente() {
        return nmCliente;
    }

    public void setNmCliente(String nmCliente) {
        this.nmCliente = nmCliente;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getDsLogin() {
        return dsLogin;
    }

    public void setDsLogin(String dsLogin) {
        this.dsLogin = dsLogin;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public String getNuDddRes() {
        return nuDddRes;
    }

    public void setNuDddRes(String nuDddRes) {
        this.nuDddRes = nuDddRes;
    }

    public String getNuDddCel() {
        return nuDddCel;
    }

    public void setNuDddCel(String nuDddCel) {
        this.nuDddCel = nuDddCel;
    }

    public String getNuDddCom() {
        return nuDddCom;
    }

    public void setNuDddCom(String nuDddCom) {
        this.nuDddCom = nuDddCom;
    }

    public String getNuTelRes() {
        return nuTelRes;
    }

    public void setNuTelRes(String nuTelRes) {
        this.nuTelRes = nuTelRes;
    }

    public String getNuTelCel() {
        return nuTelCel;
    }

    public void setNuTelCel(String nuTelCel) {
        this.nuTelCel = nuTelCel;
    }

    public String getNuTelCom() {
        return nuTelCom;
    }

    public void setNuTelCom(String nuTelCom) {
        this.nuTelCom = nuTelCom;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<ClienteEndereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<ClienteEndereco> enderecos) {
        this.enderecos = enderecos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdCliente != null ? cdCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = true;
        if (object == null) {
            isEqual = false;
        }
        if (isEqual && (!(object instanceof Cliente))) {
            isEqual = false;
        }
        final Cliente other = (Cliente) object;
        if (isEqual && (this.cdCliente != other.cdCliente
                && (this.cdCliente == null
                || !this.cdCliente.equals(other.cdCliente)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "br.com.icrm.persistence.entity.HlCliente[ cdCliente=" + cdCliente + " ]";
    }
    
}
