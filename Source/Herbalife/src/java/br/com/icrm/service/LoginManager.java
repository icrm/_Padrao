package br.com.icrm.service;

import br.com.icrm.exception.InactiveUserException;
import br.com.icrm.exception.LoginException;
import br.com.icrm.persistence.dao.UserDAO;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.MD5;
import br.com.icrm.security.Session;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "loginManager")
public class LoginManager implements Serializable {

    public static final long serialVersionUID = -1L;
    private UserDAO userDAO = new UserDAO();
    private Session session = new Session();

    public Session login(String email, String password) throws LoginException, InactiveUserException {
        User u = userDAO.findByEmail(email);
        if (u == null) {
            throw new LoginException();
        }

        if (u.getStatus().getNmStatus().equals("Inativo")) {
            throw new InactiveUserException();
        }
        try {
            password = MD5.hashMD5(password);
        } catch (NoSuchAlgorithmException ex) {
            throw new LoginException();
        }
        if (password != null && password.equals(u.getDsPassword())) {
            session.setLoggedUser(u);
            session.setLoggedUserPages(u.getGroup().getPages());
            session.setLoggedUserPolicies(u.getGroup().getPolicies());
        } else {
            throw new LoginException();
        }
        return session;
    }

    public void logout() {
        session.clear();
    }
}
