package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.Policy;
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
 * @see Policy
 */
public class PolicyDAO extends AbstractDAO<Policy> {

    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(PageStatusDAO.class);
    }

    @Override
    protected Class<Policy> getDomainClass() {
        return Policy.class;
    }

    /**
     * Método para buscar um Objeto Policy pelo Nome.
     *
     * @param name
     * @return Policy
     */
    public Policy findByName(final String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Policy.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Policy> root = criteria.from(Policy.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmPolicy", String.class)), name));

        Policy policy = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            policy = (Policy) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return policy;
    }
}
