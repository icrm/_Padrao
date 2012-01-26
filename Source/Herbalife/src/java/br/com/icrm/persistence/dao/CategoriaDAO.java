package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.Categoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

public class CategoriaDAO extends AbstractDAO<Categoria> {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(CategoriaDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("HerbalifePU");
        return emf.createEntityManager();
    }

    @Override
    protected Class<Categoria> getDomainClass() {
        return Categoria.class;
    }

    public Categoria findByName(final String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Categoria.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Categoria> root = criteria.from(Categoria.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmCategoria", String.class)), name));

        Categoria categoria = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            categoria = (Categoria) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return categoria;
    }

    public List<Categoria> findAllOrphans() {
        LOGGER.debug("Iniciando [FIND-ALL-ORPHANS] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Categoria.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Categoria> root = criteria.from(Categoria.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.isNull(root
                .get(root.getModel()
                .getSingularAttribute("pai", Categoria.class))));

        LOGGER.debug("Finalizando [FIND-ALL-ORPHANS] da Entidade "
                + getDomainClass().getName() + ".");
        return getEntityManager().createQuery(criteria).getResultList();
    }
}