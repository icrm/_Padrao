package br.com.icrm.service;

import br.com.icrm.security.Security;
import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.UserDAO;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.MD5;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 * Classe com métodos de Serviço para a Entidade User.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@SessionScoped
@ManagedBean(name = "userSerive")
public class UserService implements Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger LOGGER;
    private UserDAO userDAO = new UserDAO();
    private User user;
    
    static {
        LOGGER = Logger.getLogger(UserService.class);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public User findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getUser")) {
            throw new PermissionException(user.getNmUser(), "getUser");
        }
        return userDAO.findByName(name);
    }

    public User findByEmail(String email) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getUser")) {
            throw new PermissionException(user.getNmUser(), "getUser");
        }
        return userDAO.findByEmail(email);
    }

    public void delete(User t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteUser")) {
            throw new PermissionException(user.getNmUser(), "deleteUser");
        }
        userDAO.delete(t);
    }

    public List<User> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listUser")) {
            throw new PermissionException(user.getNmUser(), "listUser");
        }
        return userDAO.findAll();
    }

    public User findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getUser")) {
            throw new PermissionException(user.getNmUser(), "getUser");
        }
        return userDAO.findById(id);
    }

    public User insert(User t) throws ICRMException {
        try {
            t.setDsPassword(MD5.hashMD5(t.getDsPassword()));
        } catch (NoSuchAlgorithmException ex) {
            throw new ICRMException(ex);
        }
        if (!Security.checkPolicy(this.user, "insertUser")) {
            throw new PermissionException(user.getNmUser(), "insertUser");
        }
        return userDAO.insert(t);
    }

    public User update(User t) throws ICRMException {
        try {
            t.setDsPassword(MD5.hashMD5(t.getDsPassword()));
        } catch (NoSuchAlgorithmException ex) {
            throw new ICRMException(ex);
        }
        if (!Security.checkPolicy(this.user, "editUser")) {
            throw new PermissionException(user.getNmUser(), "editUser");
        }
        return userDAO.update(t);
    }
}
