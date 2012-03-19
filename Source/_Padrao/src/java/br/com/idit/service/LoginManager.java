package br.com.idit.service;

import br.com.idit.exception.InactiveUserException;
import br.com.idit.exception.LoginException;
import br.com.idit.persistence.dao.UserDAO;
import br.com.idit.persistence.entity.User;
import br.com.idit.security.MD5;
import br.com.idit.security.Session;
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
