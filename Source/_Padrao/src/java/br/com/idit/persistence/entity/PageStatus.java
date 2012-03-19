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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe da Entidade PageStatus, representa a tabela GU_PAGE_STATUS.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@Entity
@Table(name = "GU_PAGE_STATUS")
public class PageStatus implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 1875467309980202034L;
    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(PageStatus.class);
    }
    /**
     * Variável que representa o ID da tabela GU_PAGE_STATUS.
     */
    @Id
    @Column(name = "CD_STATUS")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdStatus;
    /**
     * Variável que representa o campo NM_STATUS da tabela GU_PAGE_STATUS.
     */
    @Column(name = "NM_STATUS")
    @Length(max = 60, message = "O Nome do Status não deve conter mais do que 60 caracteres.")
    @NotEmpty(message = "O Nome do Status não pode estar VAZIO.")
    private String nmStatus;
    /**
     * Variável que representa o campo DS_STATUS da tabela GU_PAGE_STATUS.
     */
    @Column(name = "DS_STATUS")
    @Length(max = 200, message = "A Descrição do Status não deve conter mais do que 200 caracteres.")
    private String dsStatus;
    /**
     * Variável que representa o campo CREATED da tabela GU_PAGE_STATUS.
     */
    @Column(name = "CREATED", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date create = new Date();
    /**
     * Variável que representa o relacionamento de um para muitos com a
     * tabela GU_PAGE.
     */
    @OneToMany(mappedBy = "status")
    private List<Page> pages = new ArrayList<Page>();

    /**
     * Construtor padrão.
     */
    public PageStatus() {
        //LOGGER.debug("Instância da Entidade PageStatus foi criada.");
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
    public Date getCreate() {
        return create;
    }

    /**
     * Define o valor da propriedade created.
     *
     * @param created - Data de criação.
     */
    public void setCreate(Date create) {
        this.create = create;
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
     * @param dsStatus - Descrição do Status.
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
     * @param nmStatus - Nome do Status.
     */
    public void setNmStatus(String nmStatus) {
        this.nmStatus = nmStatus;
    }

    /**
     * Retorna o valor da propriedade pages.
     *
     * @return String
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

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;
        if (obj == null) {
            isEqual = false;
        }
        if (isEqual && (!(obj instanceof PageStatus))) {
            isEqual = false;
        }
        final PageStatus other = (PageStatus) obj;
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
