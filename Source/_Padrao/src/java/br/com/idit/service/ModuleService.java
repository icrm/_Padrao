package br.com.idit.service;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.ChildException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.dao.ModuleDAO;
import br.com.idit.persistence.entity.Module;
import br.com.idit.persistence.entity.User;
import br.com.idit.security.Security;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "moduleService")
public class ModuleService implements Serializable {

    private static final long serialVersionUID = -1L;
    private ModuleDAO dao = new ModuleDAO();
    private User user;

    public ModuleService() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Module findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getModule")) {
            throw new PermissionException(user.getNmUser(), "getModule");
        }
        return dao.findByName(name);
    }

    public List<Module> findAllOrphans() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listModule", "systemAccess")) {
            throw new PermissionException(user.getNmUser(), "listModule");
        }
        return dao.findAllOrphans();
    }

    public Module insert(Module m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertModule")) {
            throw new PermissionException(user.getNmUser(), "insertModule");
        }
        return dao.insert(m);
    }
    
    public Module update(Module m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editModule")) {
            throw new PermissionException(user.getNmUser(), "editModule");
        }
        return dao.update(m);
    }

    public List<Module> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listModule", "systemAccess")) {
            throw new PermissionException(user.getNmUser(), "listModule");
        }
        return dao.findAll();
    }

    public void delete(Module m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteModule")) {
            throw new PermissionException(user.getNmUser(), "deleteModule");
        }
        final List<Module> modules = new ArrayList<Module>();
        modules.addAll(dao.findByFather(m));
        if (modules.size() > 0) {
            throw new ChildException("Módulo", m.getNmModule());
        }
        dao.delete(m);
    }

    public List<Module> findByFather(Module father) throws ICRMException {
        if (!Security.checkPolicy(this.user, "listModule", "systemAccess")) {
            throw new PermissionException(user.getNmUser(), "listModule");
        }
        return dao.findByFather(father);
    }

}
