package br.com.idit.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe da Entidade User, representa a tabela GU_USER.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@Entity
@Table(name = "GU_USER")
public class User implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 783321098243765483L;
    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(User.class);
    }
    /**
     * Variável que representa o ID da tabela GU_USER.
     */
    @Id
    @Column(name = "CD_USER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdUser;
    /**
     * Variável que representa o campo NM_USER da tabela GU_USER.
     */
    @Column(name = "NM_USER")
    @Length(max = 60, message = "O Nome do Usuário não deve conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome do Usuário não pode estar VAZIO.")
    private String nmUser;
    /**
     * Variável que representa o campo DS_EMAIL da tabela GU_USER.
     */
    @Column(name = "DS_EMAIL")
    @Length(max = 100, message = "O E-mail do Usuário não deve conter mais do que 100 caracteres.")
    @NotEmpty(message = "O E-mail do Usuário não pode estar VAZIO.")
    @Email(message = "O E-mail informado parece inválido!")
    private String dsEmail;
    /**
     * Variável que representa o campo DS_PASSWORD da tabela GU_USER.
     */
    @Column(name = "DS_PASSWORD")
    @Length(max = 50, message = "A Senha do Usuário não deve conter mais do que 50 caracteres.")
    @NotEmpty(message = "A Senha do Usuário não pode estar VAZIA.")
    private String dsPassword;
    /**
     * Variável que representa o relacionamento de muitos para um com a
     * tabela GU_USER_STATUS.
     */
    @ManyToOne(targetEntity = UserStatus.class)
    @JoinColumn(name = "STATUS")
    private UserStatus status;
    /**
     * Variável que representa o campo CREATED da tabela GU_USER.
     */
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
    /**
     * Variável que representa o relacionamento de muitos para um com a
     * tabela GU_GROUP.
     */
    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "CD_GROUP")
    private Group group;

    @OneToMany(mappedBy = "user", targetEntity = SecretQuestionResponse.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<SecretQuestionResponse> responses = new ArrayList<SecretQuestionResponse>();

    /**
     * Construtor padrão.
     */
    public User() {
        //LOGGER.debug("Instância da Entidade User foi criada.");
    }

    /**
     * Retorna o valor da propriedade cdUser.
     *
     * @return Long
     */
    public Long getCdUser() {
        return cdUser;
    }

    /**
     * Define o valor da propriedade cdUser.
     *
     * @param cdUser - Código do usuário a ser definido.
     */
    public void setCdUser(Long cdUser) {
        this.cdUser = cdUser;
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
     * Retorna o valor da propriedade dsEmail.
     *
     * @return String
     */
    public String getDsEmail() {
        return dsEmail;
    }

    /**
     * Define o valor da propriedade dsEmail.
     *
     * @param dsEmail - E-mail do usuário.
     */
    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail.toLowerCase();
    }

    /**
     * Retorna o valor da propriedade dsPassword.
     *
     * @return String
     */
    public String getDsPassword() {
        return dsPassword;
    }

    /**
     * Define o valor da propriedade dsPassword.
     *
     * @param dsPassword - Senha do usuário.
     */
    public void setDsPassword(final String dsPassword) {
        this.dsPassword = dsPassword;
    }

    /**
     * Retorna o valor da propriedade nmUser.
     *
     * @return String
     */
    public String getNmUser() {
        return nmUser;
    }

    /**
     * Define o valor da propriedade nmUser.
     *
     * @param nmUser - Nome do usuário.
     */
    public void setNmUser(String nmUser) {
        this.nmUser = nmUser;
    }

    /**
     * Retorna o valor da propriedade Status.
     *
     * @return String
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade Status.
     *
     * @param status - Status do usuário.
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Retorna o valor da propriedade group.
     *
     * @return String
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Define o valor da propriedade Group.
     *
     * @param group - Grupo do usuário.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    public List<SecretQuestionResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<SecretQuestionResponse> responses) {
        this.responses = responses;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && (!(obj instanceof User))) {
            isEqual = false;
        }
        final User other = (User) obj;
        if (isEqual && (this.cdUser != other.cdUser
                && (this.cdUser == null
                || !this.cdUser.equals(other.cdUser)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        int cdUserHash = 0;
        if (this.cdUser != null) {
            cdUserHash = this.cdUser.hashCode();
        }
        hash = 79 * hash + cdUserHash;
        return hash;
    }
}
