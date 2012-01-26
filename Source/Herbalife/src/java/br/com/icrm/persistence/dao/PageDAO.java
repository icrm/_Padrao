package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.Module;
import br.com.icrm.persistence.entity.Page;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 * Classe DAO da Entidade Page.
 *
 * @since 0.1
 * @version 0.1
 * @see AbstractDAO
 * @see Page
 */
public class PageDAO extends AbstractDAO<Page> {

    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(PageDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("HerbalifePU");
        return emf.createEntityManager();
    }

    @Override
    protected Class<Page> getDomainClass() {
        return Page.class;
    }

    /**
     * Método para buscar um Objeto Page pelo Nome.
     *
     * @param name
     * @return Page
     */
    public Page findByName(final String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Page.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Page> root = criteria.from(Page.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmPage", String.class)), name));

        Page page = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            page = (Page) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return page;
    }

    /**
     * Método para buscar uma lista de Objetos Page pelo Módulo.
     *
     * @param module
     * @return List<Page>
     */
    public List<Page> findByModule(final Module module) {
        LOGGER.debug("Iniciando [FIND-BY-MODULE] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Page.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Page> root = criteria.from(Page.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("module", Module.class)), module));

        LOGGER.debug("Finalizando [FIND-BY-MODULE] da Entidade "
                + getDomainClass().getName() + ".");
        return getEntityManager().createQuery(criteria).getResultList();
    }
}
