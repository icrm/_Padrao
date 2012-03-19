package br.com.idit.persistence.entity;

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
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe da Entidade Module, representa a tabela GU_MODULE.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@Entity
@Table(name = "GU_MODULE")
public class Module implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 76453782839210293L;
    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Module.class);
    }
    /**
     * Variável que representa o ID da tabela GU_MODULE.
     */
    @Id
    @Column(name = "CD_MODULE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdModule;
    /**
     * Variável que representa o relacionamento de muitos para um com a
     * tabela GU_MODULE.
     */
    @ManyToOne(targetEntity = Module.class)
    @JoinColumn(name = "CD_PARENT")
    private Module parent;
    /**
     * Variável que representa o relacionamento de um para muitos com a
     * tabela GU_MODULE.
     */
    @OneToMany(mappedBy = "parent", targetEntity = Module.class)
    private List<Module> children = new ArrayList<Module>();
    /**
     * Variável que representa o campo NM_MODULE da tabela GU_MODULE.
     */
    @Column(name = "NM_MODULE")
    @Length(max = 60, message = "O Nome do Módulo não deve conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome do Módulo não pode estar VAZIO.")
    private String nmModule;
    /**
     * Variável que representa o campo DS_MODULE da tabela GU_MODULE.
     */
    @Column(name = "DS_MODULE")
    @Length(max = 200, message = "A Descrição do Módulo não deve conter mais do que 200 caracteres.")
    private String dsModule;
    /**
     * Variável que representa o relacionamento de muitos para um com a
     * tabela GU_MODULE_STATUS.
     */
    @ManyToOne(targetEntity = ModuleStatus.class)
    @JoinColumn(name = "STATUS")
    private ModuleStatus status;
    /**
     * Variável que representa o campo CREATED da tabela GU_MODULE.
     */
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
    /**
     * Variável que representa o relacionamento de um para muitos com a
     * tabela GU_PAGE.
     */
    @OneToMany(mappedBy = "module")
    private List<Page> pages = new ArrayList<Page>();

    /**
     * Construtor padrão.
     */
    public Module() {
        //LOGGER.debug("Instância da Entidade Module foi criada.");
    }

    /**
     * Construtor com todos os parametros.
     * 
     * @param cdModule Código do Módulo.
     * @param parent Código do Módulo Pai.
     * @param nmModule Nome do Módulo.
     * @param dsModule Descrição do Módulo.
     * @param status Status do Módulo.
     */
    public Module(final Long cdModule, final Module parent,
            final String nmModule, final String dsModule,
            final ModuleStatus status) {
        this.cdModule = cdModule;
        this.parent = parent;
        this.nmModule = nmModule;
        this.dsModule = dsModule;
        this.status = status;
    }

    /**
     * Retorna o valor da propriedade cdModule.
     *
     * @return Long
     */
    public Long getCdModule() {
        return cdModule;
    }

    /**
     * Define o valor da propriedade cdModule.
     *
     * @param cdModule - Código do Módulo a ser definido.
     */
    public void setCdModule(Long cdModule) {
        this.cdModule = cdModule;
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
     * Retorna o valor da propriedade dsModule.
     *
     * @return String
     */
    public String getDsModule() {
        return dsModule;
    }

    /**
     * Define o valor da propriedade dsModule.
     *
     * @param dsModule - Descrição do Módulo.
     */
    public void setDsModule(String dsModule) {
        this.dsModule = dsModule;
    }

    /**
     * Retorna o valor da propriedade nmModule.
     *
     * @return String
     */
    public String getNmModule() {
        return nmModule;
    }

    /**
     * Define o valor da propriedade nmModule.
     *
     * @param nmModule - Nome do Módulo.
     */
    public void setNmModule(String nmModule) {
        this.nmModule = nmModule;
    }

    /**
     * Retorna o valor da propriedade parent.
     *
     * @return Module
     */
    public Module getParent() {
        return parent;
    }

    /**
     * Define o valor da propriedade parent.
     *
     * @param parent - Módulo pai.
     */
    public void setParent(Module parent) {
        this.parent = parent;
    }

    /**
     * Retorna o valor da propriedade children.
     *
     * @return List<Module>
     */
    public List<Module> getChildren() {
        return children;
    }

    /**
     * Define o valor da propriedade children.
     *
     * @param children - Módulos filhos.
     */
    public void setChildren(List<Module> children) {
        this.children = children;
    }

    /**
     * Retorna o valor da propriedade status.
     *
     * @return ModuleStatus
     */
    public ModuleStatus getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade status.
     *
     * @param status - Status do Módulo.
     */
    public void setStatus(ModuleStatus status) {
        this.status = status;
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
     * @param pages - Páginas do Módulo.
     */
    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && !(obj instanceof Module)) {
            isEqual = false;
        }
        final Module other = (Module) obj;
        if (isEqual && (this.cdModule != other.cdModule
                && (this.cdModule == null
                || !this.cdModule.equals(other.cdModule)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        int cdModuleHash = 0;
        if (this.cdModule != null) {
            cdModuleHash = this.cdModule.hashCode();
        }
        hash = 71 * hash + cdModuleHash;
        return hash;
    }
}
