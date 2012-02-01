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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe da Entidade Group, representa a tabela GU_GROUP.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@Entity
@Table(name = "GU_GROUP")
public class Group implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 198234503492873333L;
    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Module.class);
    }
    /**
     * Variável que representa o ID da tabela GU_GROUP.
     */
    @Id
    @Column(name = "CD_GROUP")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdGroup;
    /**
     * Variável que representa o campo NM_GROUP da tabela GU_MODULE.
     */
    @Column(name = "NM_GROUP")
    @Length(max = 60, message = "O Nome do Grupo não deve conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome do Grupo não deve estar VAZIO.")
    private String nmGroup;
    /**
     * Variável que representa o campo DS_GROUP da tabela GU_MODULE.
     */
    @Column(name = "DS_GROUP")
    @Length(max = 200, message = "A Descrição do Grupo não deve conter mais do que 200 caracteres.")
    private String dsGroup;
    /**
     * Variável que representa o campo CREATED da tabela GU_MODULE.
     */
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
    /**
     * Variável que representa o relacionamento de um para muitos com a
     * tabela GU_USER.
     */
    @OneToMany(mappedBy = "group")
    private List<User> users = new ArrayList<User>();
    /**
     * Variável que representa o relacionamento de muitos para muitos com a
     * tabela GU_PAGE.
     */
    @ManyToMany(targetEntity = Page.class)
    @JoinTable(name = "GU_ACCESS_PROFILE", joinColumns = {
        @JoinColumn(name = "CD_GROUP")}, inverseJoinColumns = {
        @JoinColumn(name = "CD_PAGE")})
    private List<Page> pages = new ArrayList<Page>();
    /**
     * Variável que representa o relacionamento de muitos para muitos com a
     * tabela GU_POLICY.
     */
    @ManyToMany(targetEntity = Policy.class)
    @JoinTable(name = "GU_PERMISSION", joinColumns = {
        @JoinColumn(name = "CD_GROUP")}, inverseJoinColumns = {
        @JoinColumn(name = "CD_POLICY")})
    private List<Policy> policies = new ArrayList<Policy>();

    /**
     * Construtor padrão.
     */
    public Group() {
        //LOGGER.debug("Instância da Entidade Group foi criada.");
    }

    /**
     * Retorna o valor da propriedade Group.
     *
     * @return Long
     */
    public Long getCdGroup() {
        return cdGroup;
    }

    /**
     * Define o valor da propriedade cdGroup.
     *
     * @param cdGroup - Código do Grupo a ser definido.
     */
    public void setCdGroup(Long cdGroup) {
        this.cdGroup = cdGroup;
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
     * Retorna o valor da propriedade dsGroup.
     *
     * @return String
     */
    public String getDsGroup() {
        return dsGroup;
    }

    /**
     * Define o valor da propriedade dsGroup.
     *
     * @param dsGroup - Descrição do Grupo.
     */
    public void setDsGroup(String dsGroup) {
        this.dsGroup = dsGroup;
    }

    /**
     * Retorna o valor da propriedade nmGroup.
     *
     * @return String
     */
    public String getNmGroup() {
        return nmGroup;
    }

    /**
     * Define o valor da propriedade nmGroup.
     *
     * @param nmGroup - Nome do Grupo.
     */
    public void setNmGroup(String nmGroup) {
        this.nmGroup = nmGroup;
    }

    /**
     * Retorna o valor da propriedade pages.
     *
     * @return List<Page>
     */
    public List<Page> getPages() {
        return pages;
    }

    /**
     * Define o valor da propriedade pages.
     *
     * @param pages - Lista de Páginas.
     */
    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    /**
     * Retorna o valor da propriedade policies.
     *
     * @return List<Policy>
     */
    public List<Policy> getPolicies() {
        return policies;
    }

    /**
     * Define o valor da propriedade policies.
     *
     * @param pages - Lista de Diretivas.
     */
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    /**
     * Retorna o valor da propriedade users.
     *
     * @return List<User>
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Define o valor da propriedade users.
     *
     * @param users - Lista de Usuários.
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && (!(obj instanceof Group))) {
            isEqual = false;
        }
        final Group other = (Group) obj;
        if (isEqual && (this.cdGroup != other.cdGroup
                && (this.cdGroup == null
                || !this.cdGroup.equals(other.cdGroup)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        int cdGroupHash = 0;
        if (this.cdGroup != null) {
            cdGroupHash = this.cdGroup.hashCode();
        }
        hash = 29 * hash + cdGroupHash;
        return hash;
    }
}
