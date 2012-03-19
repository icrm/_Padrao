package br.com.idit.persistence.dao;

import br.com.idit.persistence.entity.PageStatus;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 * Classe DAO da Entidade UserStatus.
 *
 * @since 0.1
 * @version 0.1
 * @see AbstractDAO
 * @see PageStatus
 */
public class PageStatusDAO extends AbstractDAO<PageStatus> {

    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(PageStatusDAO.class);
    }

    @Override
    protected Class<PageStatus> getDomainClass() {
        return PageStatus.class;
    }

    /**
     * Método para buscar um Objeto PageStatus pelo Nome.
     *
     * @param name
     * @return PageStatus
     */
    public PageStatus findByName(final String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(PageStatus.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<PageStatus> root = criteria.from(PageStatus.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmStatus", String.class)), name));

        PageStatus pageStatus = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            pageStatus = (PageStatus) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return pageStatus;
    }
}
