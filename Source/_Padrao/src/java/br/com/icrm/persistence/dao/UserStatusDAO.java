package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.UserStatus;
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
 * @see Serializable
 * @see AbstractDAO
 * @see UserStatus
 */
public class UserStatusDAO extends AbstractDAO<UserStatus> {

    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(UserStatusDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("PUPadrao");
        return emf.createEntityManager();
    }

    @Override
    protected Class<UserStatus> getDomainClass() {
        return UserStatus.class;
    }

    /**
     * Método para buscar um Objeto UserStatus pelo Nome.
     *
     * @param name - Nome do UserStatus.
     * @return UserStatus
     * @see UserStatus
     */
    public UserStatus findByName(final String name) {
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
        final CriteriaQuery criteria = cbuilder.createQuery(UserStatus.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<UserStatus> root = criteria.from(UserStatus.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmStatus", String.class)), name));

        UserStatus userStatus = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            userStatus = (UserStatus) getEntityManager()
                    .createQuery(criteria).getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return userStatus;
    }
}
