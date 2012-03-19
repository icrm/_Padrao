package br.com.idit.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe da Entidade Policy, representa a tabela GU_POLICY.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@Entity
@Table(name = "GU_POLICY")
public class Policy implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 3283756209857463L;
    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Policy.class);
    }
    /**
     * Variável que representa o ID da tabela GU_POLICY.
     */
    @Id
    @Column(name = "CD_POLICY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdPolicy;
    /**
     * Variável que representa o campo NM_POLICY da tabela GU_POLICY.
     */
    @Column(name = "NM_POLICY")
    @Length(max = 60, message = "O Nome da Diretiva não deve conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome da Diretiva não pode estar VAZIO.")
    private String nmPolicy;
    /**
     * Variável que representa o campo DS_POLICY da tabela GU_POLICY.
     */
    @Column(name = "DS_POLICY")
    @Length(max = 200, message = "A Descrição da Diretiva não deve conter mais do que 200 caracteres.")
    private String dsPolicy;
    /**
     * Variável que representa o campo NM_FUNCTION da tabela GU_POLICY.
     */
    @Column(name = "NM_FUNCTION")
    @Length(max = 60, message = "O Nome da Função desta Diretiva não deve conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome da Função desta Diretiva não pode estar VAZIO.")
    private String nmFunction;
    /**
     * Variável que representa o campo CREATED da tabela GU_POLICY.
     */
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
    /**
     * Variável que representa o relacionamento de muitos para muitos com a
     * tabela GU_GROUP.
     */
    @ManyToMany(mappedBy = "policies", targetEntity = Group.class)
    private List<Group> groups = new ArrayList<Group>();

    /**
     * Construtor padrão.
     */
    public Policy() {
        //LOGGER.debug("Instância da Entidade Policy foi criada.");
    }

    /**
     * Retorna o valor da propriedade cdPolicy.
     *
     * @return Long
     */
    public Long getCdPolicy() {
        return cdPolicy;
    }

    /**
     * Define o valor da propriedade cdPolicy.
     *
     * @param cdPolicy - Código da Diretiva a ser definido.
     */
    public void setCdPolicy(Long cdPolicy) {
        this.cdPolicy = cdPolicy;
    }

    /**
     * Retorna o valor da propriedade created.
     *
     * @return Date
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Define o valor da propriedade created.
     *
     * @param created - Data de criação.
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Retorna o valor da propriedade dsPolicy.
     *
     * @return String
     */
    public String getDsPolicy() {
        return dsPolicy;
    }

    /**
     * Define o valor da propriedade dsPolicy.
     *
     * @param dsPolicy - Descrição da Diretiva.
     */
    public void setDsPolicy(String dsPolicy) {
        this.dsPolicy = dsPolicy;
    }

    /**
     * Retorna o valor da propriedade nmFunction.
     *
     * @return String
     */
    public String getNmFunction() {
        return nmFunction;
    }

    /**
     * Define o valor da propriedade nmFunction.
     *
     * @param nmFunction - Nome da Função associada à Diretiva.
     */
    public void setNmFunction(String nmFunction) {
        this.nmFunction = nmFunction;
    }

    /**
     * Retorna o valor da propriedade nmPolicy.
     *
     * @return String
     */
    public String getNmPolicy() {
        return nmPolicy;
    }

    /**
     * Define o valor da propriedade nmPolicy.
     *
     * @param nmPolicy - Nome da Diretiva.
     */
    public void setNmPolicy(String nmPolicy) {
        this.nmPolicy = nmPolicy;
    }

    /**
     * Retorna a lista de Grupos associados a esta classe.
     *
     * @return List<Group>
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Define uma lista de Grupos associadas a esta classe.
     *
     * @param groups - Lista de Grupos.
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && (!(obj instanceof Policy))) {
            isEqual = false;
        }
        final Policy other = (Policy) obj;
        if (isEqual && (this.cdPolicy != other.cdPolicy
                && (this.cdPolicy == null
                || !this.cdPolicy.equals(other.cdPolicy)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        int cdPolicyHash = 0;
        if (this.cdPolicy != null) {
            cdPolicyHash = this.cdPolicy.hashCode();
        }
        hash = 59 * hash + cdPolicyHash;
        return hash;
    }
}
