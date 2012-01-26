package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.ModuleStatusDAO;
import br.com.icrm.persistence.entity.ModuleStatus;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "moduleStatusService")
public class ModuleStatusService implements Serializable {

    private static final long serialVersionUID = -1L;
    private ModuleStatusDAO moduleStatusDAO = new ModuleStatusDAO();
    private User user;

    public ModuleStatusService() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ModuleStatus findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getModuleStatus")) {
            throw new PermissionException(user.getNmUser(), "getModuleStatus");
        }
        return moduleStatusDAO.findByName(name);
    }

    public void delete(ModuleStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteModuleStatus")) {
            throw new PermissionException(user.getNmUser(), "deleteModuleStatus");
        }
        moduleStatusDAO.delete(t);
    }

    public List<ModuleStatus> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listModuleStatus")) {
            throw new PermissionException(user.getNmUser(), "listModuleStatus");
        }
        return moduleStatusDAO.findAll();
    }

    public ModuleStatus findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getModuleStatus")) {
            throw new PermissionException(user.getNmUser(), "getModuleStatus");
        }
        return moduleStatusDAO.findById(id);
    }

    public ModuleStatus insert(ModuleStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertModuleStatus")) {
            throw new PermissionException(user.getNmUser(), "insertModuleStatus");
        }
        return moduleStatusDAO.insert(t);
    }

    public ModuleStatus update(ModuleStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editModuleStatus")) {
            throw new PermissionException(user.getNmUser(), "editModuleStatus");
        }
        return moduleStatusDAO.update(t);
    }
}
