package br.com.idit.persistence.dao;

import br.com.idit.persistence.entity.SecretQuestion;
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
                .getSingularAttribute("dsPassphrase", String.class)), description));

        SecretQuestion passphrase = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através da Descrição [" + description + "].");
            passphrase = (SecretQuestion) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através da Descrição [" + description + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-DESCRIPTION] da Entidade "
                + getDomainClass().getName() + ".");
        return passphrase;
    }

}
