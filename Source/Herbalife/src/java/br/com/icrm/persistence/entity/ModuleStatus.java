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
 * Classe da Entidade ModuleStatus, representa a tabela GU_MODULE_STATUS.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@Entity
@Table(name = "GU_MODULE_STATUS")
public class ModuleStatus implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 293385647382738812L;
    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Module.class);
    }
    /**
     * Variável que representa o ID da tabela GU_MODULE_STATUS.
     */
    @Id
    @Column(name = "CD_STATUS")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdStatus;
    /**
     * Variável que representa o NM_STATUS da tabela GU_MODULE_STATUS.
     */
    @Column(name = "NM_STATUS")
    @Length(max = 60, message = "O Nome do Status não pode conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome do Status não pode estar VAZIO.")
    private String nmStatus;
    /**
     * Variável que representa o DS_STATUS da tabela GU_MODULE_STATUS.
     */
    @Column(name = "DS_STATUS")
    @Length(max = 200, message = "A Descrição do Status não pode conter mais do que 200 caracteres.")
    private String dsStatus;
    /**
     * Variável que representa o CREATED da tabela GU_MODULE_STATUS.
     */
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    /**
     * Variável que representa o relacionamento de um para muitos com a
     * tabela GU_MODULE.
     */
    @OneToMany(mappedBy = "status", targetEntity = Module.class)
    private List<Module> modules = new ArrayList<Module>();

    /**
     * Construtor padrão.
     */
    public ModuleStatus() {
        //LOGGER.debug("Instância da Entidade ModuleStatus foi criada.");
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
     * Define o valor da propriedade cdStatus.
     *
     * @param cdStatus - Código do Status a ser definido.
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
     * @param dsStatus - Descrição do status.
     */
    public void setDsStatus(String dsStatus) {
        this.dsStatus = dsStatus;
    }

    /**
     * Retorna o valor da propriedade nmStatus.
     *
     * @return String
     */
    public String getNmStatus() {
        return nmStatus;
    }

    /**
     * Define o valor da propriedade nmStatus.
     *
     * @param nmStatus - Nome do status.
     */
    public void setNmStatus(String nmStatus) {
        this.nmStatus = nmStatus;
    }

    /**
     * Retorna o valor da propriedade modules.
     *
     * @return List<Module>
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * Define o valor da propriedade modules.
     *
     * @param modules - Lista de Módulos.
     */
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && !(obj instanceof ModuleStatus)) {
            isEqual = false;
        }
        final ModuleStatus other = (ModuleStatus) obj;
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
