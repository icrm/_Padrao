package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.CategoriaDAO;
import br.com.icrm.persistence.entity.Categoria;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "categoriaService")
public class CategoriaService implements Serializable {
    
    private static final long serialVersionUID = 6439283129832392L;
    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Categoria findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getCategoria")) {
            throw new PermissionException(user.getNmUser(), "getCategoria");
        }
        return categoriaDAO.findByName(name);
    }

    public List<Categoria> findAllOrphans() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listCategoria")) {
            throw new PermissionException(user.getNmUser(), "listCategoria");
        }
        return categoriaDAO.findAllOrphans();
    }

    public Categoria insert(Categoria m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertCategoria")) {
            throw new PermissionException(user.getNmUser(), "insertCategoria");
        }
        return categoriaDAO.insert(m);
    }
    
    public Categoria update(Categoria m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editCategoria")) {
            throw new PermissionException(user.getNmUser(), "editCategoria");
        }
        return categoriaDAO.update(m);
    }

    public List<Categoria> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listCategoria")) {
            throw new PermissionException(user.getNmUser(), "listCategoria");
        }
        return categoriaDAO.findAll();
    }

    public void delete(Categoria c) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteCategoria")) {
            throw new PermissionException(user.getNmUser(), "deleteCategoria");
        }
        categoriaDAO.delete(c);
    }
}
