package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.Venda;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;

public class VendaDAO extends AbstractDAO<Venda> {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(VendaDAO.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf 
                = Persistence.createEntityManagerFactory("HerbalifePU");
        return emf.createEntityManager();
    }

    @Override
    protected Class<Venda> getDomainClass() {
        return Venda.class;
    }
}