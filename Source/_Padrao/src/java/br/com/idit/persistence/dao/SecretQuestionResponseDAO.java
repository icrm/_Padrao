package br.com.idit.persistence.dao;

import br.com.idit.persistence.entity.SecretQuestionResponse;
import org.apache.log4j.Logger;

public class SecretQuestionResponseDAO extends AbstractDAO<SecretQuestionResponse> {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(SecretQuestionResponseDAO.class);
    }

    @Override
    protected Class<SecretQuestionResponse> getDomainClass() {
        return SecretQuestionResponse.class;
    }

}
