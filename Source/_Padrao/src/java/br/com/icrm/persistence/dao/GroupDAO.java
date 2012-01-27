package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.Group;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 * Classe DAO da Entidade Grupo.
 *
 * @since 0.1
 * @version 0.1
 * @see AbstractDAO
 * @see Group
 */
public class GroupDAO extends AbstractDAO<Group> {

    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(GroupDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("PUPadrao");
        return emf.createEntityManager();
    }

    @Override
    protected Class<Group> getDomainClass() {
        return Group.class;
    }

    /**
     * Método para buscar um Objeto Group pelo Nome.
     *
     * @param name
     * @return Group
     */
    public Group findByName(final String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        if ("".equals(name.trim()))  {
            LOGGER.debug("O Nome não foi informado.");
            return null;
        }
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Group.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Group> root = criteria.from(Group.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmGroup", String.class)), name));

        Group group = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            group = (Group) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return group;
    }
}
