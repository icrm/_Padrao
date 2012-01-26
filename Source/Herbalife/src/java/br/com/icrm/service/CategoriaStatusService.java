package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.CategoriaStatusDAO;
import br.com.icrm.persistence.entity.CategoriaStatus;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "categoriaStatusService")
public class CategoriaStatusService implements Serializable {

    private static final long serialVersionUID = 7654398202376432L;
    private CategoriaStatusDAO categoriaStatusDAO = new CategoriaStatusDAO();
    private User user;

    public CategoriaStatusService() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CategoriaStatus findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getCategoriaStatus")) {
            throw new PermissionException(user.getNmUser(), "getCategoriaStatus");
        }
        return categoriaStatusDAO.findByName(name);
    }

    public void delete(CategoriaStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteCategoriaStatus")) {
            throw new PermissionException(user.getNmUser(), "deleteCategoriaStatus");
        }
        categoriaStatusDAO.delete(t);
    }

    public List<CategoriaStatus> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listCategoriaStatus")) {
            throw new PermissionException(user.getNmUser(), "listCategoriaStatus");
        }
        return categoriaStatusDAO.findAll();
    }

    public CategoriaStatus findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getCategoriaStatus")) {
            throw new PermissionException(user.getNmUser(), "getCategoriaStatus");
        }
        return categoriaStatusDAO.findById(id);
    }

    public CategoriaStatus insert(CategoriaStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertCategoriaStatus")) {
            throw new PermissionException(user.getNmUser(), "insertCategoriaStatus");
        }
        return categoriaStatusDAO.insert(t);
    }

    public CategoriaStatus update(CategoriaStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editCategoriaStatus")) {
            throw new PermissionException(user.getNmUser(), "editCategoriaStatus");
        }
        return categoriaStatusDAO.update(t);
    }
}
