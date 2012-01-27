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
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe da Entidade UserStatus, representa a tabela GU_USER_STATUS.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@Entity
@Table(name = "GU_USER_STATUS")
public class UserStatus implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 2109923902199234L;
    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(UserStatus.class);
    }
    /**
     * Variável que representa o ID da tabela GU_USER_STATUS.
     */
    @Id
    @Column(name = "CD_STATUS")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdStatus;
    /**
     * Variável que representa o campo NM_STATUS da tabela GU_USER_STATUS.
     */
    @Column(name = "NM_STATUS")
    @Length(max = 60, message = "O Nome do Status não deve conter mais do que 60 caracteres!")
    @NotEmpty(message = "O Nome do Status não pode estar VAZIO.")
    private String nmStatus;
    /**
     * Variável que representa o campo DS_STATUS da tabela GU_USER_STATUS.
     */
    @Column(name = "DS_STATUS")
    @Length(max = 200, message = "A Descrição do Status não deve conter mais do que 200 caracteres!")
    private String dsStatus;
    /**
     * Variável que representa o campo CREATED da tabela GU_USER_STATUS.
     */
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    /**
     * Variável que representa o relacionamento de um para muitos com a
     * tabela GU_USER.
     */
    @OneToMany(mappedBy = "status", targetEntity = User.class)
    private List<User> users = new ArrayList<User>();

    /**
     * Construtor padrão.
     */
    public UserStatus() {
        //LOGGER.debug("Instância da Entidade UserStatus foi criada.");
    }

    /**
     * Retorna o valor da propriedade cdStatus.
     *
     * @return Long
     */
    public Long getCdStatus() {
        return cdStatus;
    }

    /**
     * Define o valor da propriedade cdStatus
     *
     * @param cdStatus - Código do status a ser definido.
     */
    public void setCdStatus(Long cdStatus) {
        this.cdStatus = cdStatus;
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
     * Retorna o valor da propriedade dsStatus.
     *
     * @return String
     */
    public String getDsStatus() {
        return dsStatus;
    }

    /**
     * Define o valor da propriedade dsStatus.
     *
     * @param dsStatus - Descrição do Status
     */
    public void setDsStatus(String dsStatus) {
        this.dsStatus = dsStatus;
    }

    /**
     * Retornar o valor da propriedade nmStatus.
     *
     * @return String
     */
    public String getNmStatus() {
        return nmStatus;
    }

    /**
     * Define o valor da propriedade nmStatus.
     * 
     * @param nmStatus - Nome do Status.
     */
    public void setNmStatus(String nmStatus) {
        this.nmStatus = nmStatus;
    }

    /**
     * Retorna um lista com os usuários que se relacionam com esta classe.
     *
     * @return List<User>
     * @see User
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Define uma lista com os Usuários que se relacionam com esta classe.
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
        if (isEqual && (!(obj instanceof UserStatus))) {
            isEqual = false;
        }
        final UserStatus other = (UserStatus) obj;
        if (isEqual && (this.cdStatus != other.cdStatus
                && (this.cdStatus == null
                || !this.cdStatus.equals(other.cdStatus)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        int cdStatusHash = 0;
        if (this.cdStatus != null) {
            cdStatusHash = this.cdStatus.hashCode();
        }
        hash = 89 * hash + cdStatusHash;
        return hash;
    }
}
