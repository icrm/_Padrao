package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

public class ClienteDAO extends AbstractDAO<Cliente> {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(ClienteDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("HerbalifePU");
        return emf.createEntityManager();
    }

    @Override
    protected Class<Cliente> getDomainClass() {
        return Cliente.class;
    }

    /**
     * Método para buscar um Objeto Cliente pelo Nome.
     *
     * @param name
     * @return User
     */
    public Cliente findByName(final String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Cliente.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Cliente> root = criteria.from(Cliente.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmCliente", String.class)), name));

        Cliente cliente = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            cliente = (Cliente) getEntityManager()
                    .createQuery(criteria).getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return cliente;
    }

    /**
     * Método para buscar um Objeto User pelo e-mail.
     *
     * @param email
     * @return User
     */
    public Cliente findByEmail(final String email) {
        LOGGER.debug("Iniciando [FIND-BY-EMAIL] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Cliente.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Cliente> root = criteria.from(Cliente.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("dsEmail", String.class)), email));

        Cliente cliente = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Email [" + email + "].");
            cliente = (Cliente) getEntityManager()
                    .createQuery(criteria).getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Email [" + email + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-EMAIL] da Entidade "
                + getDomainClass().getName() + ".");
        return cliente;
    }

    /**
     * Método para buscar um Objeto Cliente cujo nome começe com a string name.
     *
     * @param name
     * @return User
     */
    public List<Cliente> findByNameStartsLike(String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME-STARTS-LIKE] da Entidade "
                + getDomainClass().getName() + ".");
        if ("".equals(name.trim())) {
            LOGGER.debug("O Nome está vázio");
            LOGGER.debug("Finalizando [FIND-BY-NAME-STARTS-LIKE] da Entidade "
                + getDomainClass().getName() + ".");
            return null;
        }
        name = name.trim();
        LOGGER.debug("Nome procurado [" + name + "].");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Cliente.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Cliente> root = criteria.from(Cliente.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.like(root
                .get(root.getModel()
                .getSingularAttribute("nmCliente", String.class)), name+"%"));

        List<Cliente> clientes = new ArrayList<Cliente>();
        try {
            clientes = getEntityManager().createQuery(criteria).getResultList();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME-STARTS-LIKE] da Entidade "
                + getDomainClass().getName() + ".");
        return clientes;
    }
}