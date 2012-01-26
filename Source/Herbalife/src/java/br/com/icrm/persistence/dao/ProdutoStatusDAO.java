package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.ProdutoStatus;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

public class ProdutoStatusDAO extends AbstractDAO<ProdutoStatus> {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(ProdutoStatusDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("HerbalifePU");
        return emf.createEntityManager();
    }

    @Override
    protected Class<ProdutoStatus> getDomainClass() {
        return ProdutoStatus.class;
    }

    public ProdutoStatus findByName(final String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria
                = cbuilder.createQuery(ProdutoStatus.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<ProdutoStatus> root = criteria.from(ProdutoStatus.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root.get(
                root.getModel()
                .getSingularAttribute("nmStatus", String.class)), name));

        ProdutoStatus produtoStatus = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            produtoStatus = (ProdutoStatus) getEntityManager()
                    .createQuery(criteria).getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return produtoStatus;
    }
}