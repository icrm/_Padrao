package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.PageDAO;
import br.com.icrm.persistence.entity.Module;
import br.com.icrm.persistence.entity.Page;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "pageService")
public class PageService implements Serializable {

    private static final long serialVersionUID = -1L;
    private PageDAO pageDAO = new PageDAO();
    private User user;

    public PageService() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Page findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getPage")) {
            throw new PermissionException(user.getNmUser(), "getPage");
        }
        return pageDAO.findByName(name);
    }

    public List<Page> findByModule(Module module) throws ICRMException {
        if (!Security.checkPolicy(this.user, "listPage")) {
            throw new PermissionException(user.getNmUser(), "listPage");
        }
        return pageDAO.findByModule(module);
    }

    public Page insert(Page p) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertPage")) {
            throw new PermissionException(user.getNmUser(), "insertPage");
        }
        return pageDAO.insert(p);
    }

    public void delete(Page p) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deletePage")) {
            throw new PermissionException(user.getNmUser(), "deletePage");
        }
        pageDAO.delete(p);
    }

    public List<Page> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listPage")) {
            throw new PermissionException(user.getNmUser(), "listPage");
        }
        return pageDAO.findAll();
    }

    public Page findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getPage")) {
            throw new PermissionException(user.getNmUser(), "getPage");
        }
        return pageDAO.findById(id);
    }

    public Page update(Page t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editPage")) {
            throw new PermissionException(user.getNmUser(), "editPage");
        }
        return pageDAO.update(t);
    }
}
