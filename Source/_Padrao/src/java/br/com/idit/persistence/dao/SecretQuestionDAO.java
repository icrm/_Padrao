package br.com.idit.persistence.dao;

import br.com.idit.persistence.entity.SecretQuestion;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

public class SecretQuestionDAO extends AbstractDAO<SecretQuestion> {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(SecretQuestionDAO.class);
    }

    @Override
    protected Class<SecretQuestion> getDomainClass() {
        return SecretQuestion.class;
    }

    public SecretQuestion findByDescription(final String description) {
        LOGGER.debug("Iniciando [FIND-BY-DESCRIPTION] da Entidade "
                + getDomainClass().getName() + ".");
        if ("".equals(description.trim()))  {
            LOGGER.debug("A Descrição não foi informada.");
            return null;
        }
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(SecretQuestion.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<SecretQuestion> root = criteria.from(SecretQuestion.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root.get(root.getModel()
                .getSingularAttribute("dsQuestion", String.class)), description));

        SecretQuestion question = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através da Descrição [" + description + "].");
            question = (SecretQuestion) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através da Descrição [" + description + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-DESCRIPTION] da Entidade "
                + getDomainClass().getName() + ".");
        return question;
    }

    public List<SecretQuestion> findAllActive() {
        LOGGER.debug("Iniciando [FIND-ALL-ACTIVE] da Entidade "
                + getDomainClass().getName() + ".");
        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(SecretQuestion.class);

        LOGGER.debug("Criando Instância de Root.");
        final Root<SecretQuestion> root = criteria.from(SecretQuestion.class);

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root.get(root.getModel()
                .getSingularAttribute("active", String.class)), "S"));

        List<SecretQuestion> questions = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " que estão Ativos.");
            questions = getEntityManager().createQuery(criteria)
                    .getResultList();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " que estão Ativos.", ex);
        }

        LOGGER.debug("Finalizando [FIND-ALL-ACTIVE] da Entidade "
                + getDomainClass().getName() + ".");
        return questions;
    }
}
