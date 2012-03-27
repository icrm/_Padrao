package br.com.idit.service;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.persistence.dao.UserDAO;
import br.com.idit.persistence.entity.User;
import br.com.idit.security.MD5;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "recoverPasswordService")
public class RecoverPasswordService implements Serializable {

    private static final long serialVersionUID = 837712344322991233L;
    private UserDAO dao = new UserDAO();

    public User findByEmail(final String email) {
        return dao.findByEmail(email);
    }

    public void changePassword(User user) throws ICRMException {
        try {
            user.setDsPassword(MD5.hashMD5(user.getDsPassword()));
        } catch (NoSuchAlgorithmException ex) {
            throw new ICRMException(ex);
        }
        dao.update(user);
    }
}
