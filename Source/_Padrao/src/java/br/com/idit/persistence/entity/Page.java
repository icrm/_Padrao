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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe da Entidade Page, representa a tabela GU_PAGE.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@Entity
@Table(name = "GU_PAGE")
public class Page implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 44993849301843423L;
    /**
     * Objeto de log.
     */
    private static final Logger LOOGGER;

    static {
        LOOGGER = Logger.getLogger(Page.class);
    }
    /**
     * Variável que representa o ID da tabela GU_PAGE.
     */
    @Id
    @Column(name = "CD_PAGE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdPage;
    /**
     * Variável que representa o relacionamento de muitos para um com a
     * tabela GU_MODULE.
     */
    @ManyToOne(targetEntity = Module.class)
    @JoinColumn(name = "CD_MODULE")
    private Module module;
    /**
     * Variável que representa o NM_PAGE da tabela GU_PAGE.
     */
    @Column(name = "NM_PAGE")
    @Length(max = 60, message = "O Nome da Página não deve conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome da Página não pode estar VAZIO.")
    private String nmPage;
    /**
     * Variável que representa o DS_PAGE da tabela GU_PAGE.
     */
    @Column(name = "DS_PAGE")
    @Length(max = 200, message = "A Descrição da Página não deve conter mais do que 200 caracteres.")
    private String dsPage;
    /**
     * Variável que representa o DS_URL da tabela GU_PAGE.
     */
    @Column(name = "DS_URL")
    @Length(max = 100, message = "A URL não deve conter mais do que 100 caracteres.")
    @NotEmpty(message = "A URL não pode estar VAZIA.")
    private String dsURL;
    /**
     * Variável que representa o FG_MAIN da tabela GU_PAGE.
     */
    @Column(name = "FG_MAIN")
    private byte fgMain;
    /**
     * Variável que representa o FG_SHOW_MWNU da tabela GU_PAGE
     */
    @Column(name = "FG_SHOW_MENU")
    private byte fgShowMenu;
    /**
     * Variável que representa o relacionamento de muitos para um com a
     * tabela GU_PAGE_STATUS.
     */
    @ManyToOne(targetEntity = PageStatus.class)
    @JoinColumn(name = "STATUS")
    private PageStatus status;
    /**
     * Variável que representa o CREATED da tabela GU_PAGE.
     */
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();
    /**
     * Variável que representa o relacionamento de muitos para muitos com a
     * tabela GU_GROUP.
     */
    @ManyToMany(mappedBy = "pages", targetEntity = Group.class)
    private List<Group> groups = new ArrayList<Group>();

    /**
     * Construtor padrão.
     */
    public Page() {
        //LOOGGER.debug("Instância da Entidade Page foi criada.");
    }

    /**
     * Retorna o valor da propriedade cdPage.
     *
     * @return Long
     */
    public Long getCdPage() {
        return cdPage;
    }

    /**
     * Define o valor da propriedade cdPage.
     *
     * @param cdPage - Código da Página a ser definido.
     */
    public void setCdPage(Long cdPage) {
        this.cdPage = cdPage;
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
     * Retorna o valor da propriedade dsPage.
     *
     * @return String
     */
    public String getDsPage() {
        return dsPage;
    }

    /**
     * Define o valor da propriedade dsPage.
     *
     * @param dsPage - Descrição da Página.
     */
    public void setDsPage(String dsPage) {
        this.dsPage = dsPage;
    }

    /**
     * Retorna o valor da propriedade dsURL.
     *
     * @return String
     */
    public String getDsURL() {
        return dsURL;
    }

    /**
     * Define o valor da propriedade dsURL.
     *
     * @param dsURL - URL da página.
     */
    public void setDsURL(String dsURL) {
        this.dsURL = dsURL;
    }

    /**
     * Retorna o valor da propriedade module.
     *
     * @return Module
     */
    public Module getModule() {
        return module;
    }

    /**
     * Define o valor da propriedade module.
     *
     * @param module - Módulo da página.
     */
    public void setModule(Module module) {
        this.module = module;
    }

    /**
     * Retorna o valor da propriedade nmPage.
     *
     * @return String
     */
    public String getNmPage() {
        return nmPage;
    }

    /**
     * Define o valor da propriedade nmPage.
     *
     * @param nmPage - Nome da página.
     */
    public void setNmPage(String nmPage) {
        this.nmPage = nmPage;
    }

    /**
     * Retorna o valor da propriedade status.
     *
     * @return PageStatus
     */
    public PageStatus getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade status.
     *
     * @param status - Status da página.
     */
    public void setStatus(PageStatus status) {
        this.status = status;
    }

    /**
     * Retorna o valor da propriedade groups.
     *
     * @return Group
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Define o valor da propriedade groups.
     *
     * @param groups - Grupos da página.
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    /**
     * Retorna o valor da propriedade fgMain.
     *
     * @return byte
     */
    public byte getFgMain() {
        return fgMain;
    }

    /**
     * Define o valor da propriedade fgMain.
     *
     * @param fgMain - Flag de página principal.
     */
    public void setFgMain(byte fgMain) {
        this.fgMain = fgMain;
    }

    /**
     * Retorna o valor da propriedade fgShowMenu
     *
     * @return byte
     */
    public byte getFgShowMenu() {
        return fgShowMenu;
    }

    /**
     * Define o valor da propriedade fgShowMenu
     *
     * @param fgShowMenu - Flag para exibir a página no Menu
     */
    public void setFgShowMenu(byte fgShowMenu) {
        this.fgShowMenu = fgShowMenu;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && (!(obj instanceof Page))) {
            isEqual = false;
        }
        final Page other = (Page) obj;
        if (isEqual && (this.cdPage != other.cdPage
                && (this.cdPage == null
                || !this.cdPage.equals(other.cdPage)))) {
            isEqual = false;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        int cdPageHash = 0;
        if (this.cdPage != null) {
            cdPageHash = this.cdPage.hashCode();
        }
        hash = 97 * hash + cdPageHash;
        return hash;
    }

    @Override
    public String toString() {
        return nmPage;
    }
}
