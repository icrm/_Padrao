package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.ClienteEndereco;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

public class ClienteEnderecoDAO extends AbstractDAO<ClienteEndereco> {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(ClienteEnderecoDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("HerbalifePU");
        return emf.createEntityManager();
    }

    @Override
    protected Class<ClienteEndereco> getDomainClass() {
        return ClienteEndereco.class;
    }

    public ClienteEndereco findByName(final String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        if ("".equals(name.trim())) {
            LOGGER.debug("O Nome está Vazio.");
            return null;
        }
        final String nome = name.trim();
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(ClienteEndereco.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<ClienteEndereco> root = criteria.from(ClienteEndereco.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmEndereco", String.class)), nome));

        ClienteEndereco endereco = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + nome + "].");
            endereco = (ClienteEndereco) getEntityManager()
                    .createQuery(criteria).getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + nome + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return endereco;
    }
}