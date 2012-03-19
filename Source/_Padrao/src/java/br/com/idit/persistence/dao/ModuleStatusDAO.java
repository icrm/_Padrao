package br.com.idit.persistence.dao;

import br.com.idit.persistence.entity.ModuleStatus;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 * Classe DAO da Entidade ModuleStatus.
 *
 * @since 0.1
 * @version 0.1
 * @see AbstractDAO
 * @see ModuleStatus
 */
public class ModuleStatusDAO extends AbstractDAO<ModuleStatus> {

    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(ModuleStatusDAO.class);
    }

    @Override
    protected Class<ModuleStatus> getDomainClass() {
        return ModuleStatus.class;
    }

    /**
     * Método para buscar um Objeto ModuleStatus pelo Nome.
     *
     * @param name
     * @return 
     */
    public ModuleStatus findByName(String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        if ("".equals(name.trim())) {
            return null;
        }
        name = name.trim();
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(ModuleStatus.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<ModuleStatus> root = criteria.from(ModuleStatus.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmStatus", String.class)), name));

        ModuleStatus moduleStatus = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            moduleStatus = (ModuleStatus) getEntityManager()
                    .createQuery(criteria).getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return moduleStatus;
    }
}
