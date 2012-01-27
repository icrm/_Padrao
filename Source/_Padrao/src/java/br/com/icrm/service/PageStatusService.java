package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.PageStatusDAO;
import br.com.icrm.persistence.entity.PageStatus;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "pageStatusService")
public class PageStatusService implements Serializable {

    private static final long serialVersionUID = -1L;
    private PageStatusDAO pageStatusDAO = new PageStatusDAO();
    private User user;

    public PageStatusService() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PageStatus findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getPageStatus")) {
            throw new PermissionException(user.getNmUser(), "getPageStatus");
        }
        return pageStatusDAO.findByName(name);
    }

    public void delete(PageStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deletePageStatus")) {
            throw new PermissionException(user.getNmUser(), "deletePageStatus");
        }
        pageStatusDAO.delete(t);
    }

    public List<PageStatus> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listPageStatus")) {
            throw new PermissionException(user.getNmUser(), "listPageStatus");
        }
        return pageStatusDAO.findAll();
    }

    public PageStatus findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getPageStatus")) {
            throw new PermissionException(user.getNmUser(), "getPageStatus");
        }
        return pageStatusDAO.findById(id);
    }

    public PageStatus insert(PageStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertPageStatus")) {
            throw new PermissionException(user.getNmUser(), "insertPageStatus");
        }
        return pageStatusDAO.insert(t);
    }

    public PageStatus update(PageStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editPageStatus")) {
            throw new PermissionException(user.getNmUser(), "editPageStatus");
        }
        return pageStatusDAO.update(t);
    }
}
