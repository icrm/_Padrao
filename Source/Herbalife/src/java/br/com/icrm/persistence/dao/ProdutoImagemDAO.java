package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.ProdutoImagem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;

public class ProdutoImagemDAO extends AbstractDAO<ProdutoImagem> {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(ProdutoImagemDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("HerbalifePU");
        return emf.createEntityManager();
    }

    @Override
    protected Class<ProdutoImagem> getDomainClass() {
        return ProdutoImagem.class;
    }
}