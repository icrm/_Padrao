package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.User;
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
 * @see User
 */
public class UserDAO extends AbstractDAO<User> {

    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(UserDAO.class);
    }

    @Override
    protected Class<User> getDomainClass() {
        return User.class;
    }

    /**
     * Método para buscar um Objeto User pelo Nome.
     *
     * @param name
     * @return User
     */
    public User findByName(final String name) {
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
        final CriteriaQuery criteria = cbuilder.createQuery(User.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<User> root = criteria.from(User.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root.get(root.getModel()
                .getSingularAttribute("nmUser", String.class)), name));

        User user = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            user = (User) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return user;
    }

    /**
     * Método para buscar um Objeto User pelo e-mail.
     *
     * @param email
     * @return User
     */
    public User findByEmail(final String email) {
        LOGGER.debug("Iniciando [FIND-BY-EMAIL] da Entidade "
                + getDomainClass().getName() + ".");
        if ("".equals(email.trim()))  {
            LOGGER.debug("O E-mail não foi informado.");
            return null;
        }
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(User.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<User> root = criteria.from(User.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root.get(root.getModel()
                .getSingularAttribute("dsEmail", String.class)), email));

        User user = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Email [" + email + "].");
            user = (User) getEntityManager()
                    .createQuery(criteria).getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Email [" + email + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-EMAIL] da Entidade "
                + getDomainClass().getName() + ".");
        return user;
    }
}
