package br.com.idit.service;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.dao.SecretQuestionDAO;
import br.com.idit.persistence.entity.SecretQuestion;
import br.com.idit.persistence.entity.User;
import br.com.idit.security.Security;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;

public class SecretQuestionService implements Serializable {
    
    private static final long serialVersionUID = 8728349218231234L;
    private static final Logger LOGGER;
    private SecretQuestionDAO dao = new SecretQuestionDAO();
    private User user;
    
    static {
        LOGGER = Logger.getLogger(SecretQuestionService.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SecretQuestion findByDescription(final String description) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getSecretQuestion", "insertUser")) {
            throw new PermissionException(user.getNmUser(), "getSecretQuestion");
        }
        return dao.findByDescription(description);
    }

    public List<SecretQuestion> findAllActive() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listSecretQuestion", "insertUser")) {
            throw new PermissionException(user.getNmUser(), "listSecretQuestion");
        }
        return dao.findAllActive();
    }

    public List<SecretQuestion> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listSecretQuestion", "insertUser")) {
            throw new PermissionException(user.getNmUser(), "listSecretQuestion");
        }
        return dao.findAll();
    }

    public SecretQuestion findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getSecretQuestion", "insertUser")) {
            throw new PermissionException(user.getNmUser(), "getSecretQuestion");
        }
        return dao.findById(id);
    }

    public SecretQuestion insert(SecretQuestion passphrase) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertSecretQuestion")) {
            throw new PermissionException(user.getNmUser(), "insertSecretQuestion");
        }
        return dao.insert(passphrase);
    }

    public SecretQuestion update(SecretQuestion passphrase) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editSecretQuestion")) {
            throw new PermissionException(user.getNmUser(), "editSecretQuestion");
        }
        return dao.update(passphrase);
    }

    public void delete(SecretQuestion passphrase) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteSecretQuestion")) {
            throw new PermissionException(user.getNmUser(), "deleteSecretQuestion");
        }
        dao.delete(passphrase);
    }
}
