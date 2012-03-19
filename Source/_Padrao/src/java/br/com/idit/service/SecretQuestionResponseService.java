package br.com.idit.service;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.dao.SecretQuestionResponseDAO;
import br.com.idit.persistence.entity.SecretQuestionResponse;
import br.com.idit.persistence.entity.User;
import br.com.idit.security.Security;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;

public class SecretQuestionResponseService implements Serializable {
    private static final long serialVersionUID = 773482968734234L;
    private static final Logger LOGGER;
    private SecretQuestionResponseDAO dao = new SecretQuestionResponseDAO();
    private User user;

    static {
        LOGGER = Logger.getLogger(SecretQuestionResponseService.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SecretQuestionResponse> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listSecretQuestionResponse")) {
            throw new PermissionException(user.getNmUser(), "listSecretQuestionResponse");
        }
        return dao.findAll();
    }

    public SecretQuestionResponse findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getSecretQuestionResponse")) {
            throw new PermissionException(user.getNmUser(), "getSecretQuestionResponse");
        }
        return dao.findById(id);
    }

    public SecretQuestionResponse insert(SecretQuestionResponse response) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertSecretQuestionResponse")) {
            throw new PermissionException(user.getNmUser(), "insertSecretQuestionResponse");
        }
        return dao.insert(response);
    }

    public SecretQuestionResponse update(SecretQuestionResponse response) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editSecretQuestionResponse")) {
            throw new PermissionException(user.getNmUser(), "editSecretQuestionResponse");
        }
        return dao.update(response);
    }

    public void delete(SecretQuestionResponse response) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteSecretQuestionResponse")) {
            throw new PermissionException(user.getNmUser(), "deleteSecretQuestionResponse");
        }
        dao.delete(response);
    }
}
