package br.com.icrm.persistence.dao;

import br.com.icrm.persistence.entity.Module;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 * Classe DAO da Entidade Module.
 *
 * @since 0.1
 * @version 0.1
 * @see AbstractDAO
 * @see Module
 */
public class ModuleDAO extends AbstractDAO<Module> {

    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(ModuleDAO.class);
    }

    @Override
    protected Class<Module> getDomainClass() {
        return Module.class;
    }

    /**
     * Método para buscar um Objeto Module pelo Nome.
     *
     * @param name
     * @return Module
     */
    public Module findByName(String name) {
        LOGGER.debug("Iniciando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        if ("".equals(name.trim())) {
            return null;
        }
        LOGGER.debug("Retirando espaços iniciais e finais do Nome.");
        name = name.trim();
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Module.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Module> root = criteria.from(Module.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute("nmModule", String.class)), name));

        Module module = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].");
            module = (Module) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Nome [" + name + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-NAME] da Entidade "
                + getDomainClass().getName() + ".");
        return module;
    }

    /**
     * Método para buscar uma lista de Objetos Module que não tenham Pai.
     *
     * @return List<Module>
     */
    public List<Module> findAllOrphans() {
        LOGGER.debug("Iniciando [FIND-ALL-ORPHANS] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Module.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Module> root = criteria.from(Module.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.isNull(root
                .get(root.getModel()
                .getSingularAttribute("parent", Module.class))));

        LOGGER.debug("Finalizando [FIND-ALL-ORPHANS] da Entidade "
                + getDomainClass().getName() + ".");
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    /**
     * Método para buscar uma liste de Objetos filhos.
     *
     * @return List<Module>
     */
    public List<Module> findByFather(final Module father) {
        LOGGER.debug("Iniciando [FIND-BY-FATHER] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(Module.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<Module> root = criteria.from(Module.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root.get(root.getModel()
                .getSingularAttribute("parent", Module.class)), father));

        LOGGER.debug("Finalizando [FIND-BY-FATHER] da Entidade "
                + getDomainClass().getName() + ".");
        return getEntityManager().createQuery(criteria).getResultList();
    }
}
