package br.com.idit.service;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.dao.GroupDAO;
import br.com.idit.persistence.entity.Group;
import br.com.idit.persistence.entity.User;
import br.com.idit.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "groupService")
public class GroupService implements Serializable {

    private static final long serialVersionUID = -1L;
    private GroupDAO groupDAO = new GroupDAO();
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Group findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getGroup", "insertUser")) {
            throw new PermissionException(user.getNmUser(), "getGroup");
        }
        return groupDAO.findByName(name);
    }

    public void delete(Group t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteGroup")) {
            throw new PermissionException(user.getNmUser(), "deleteGroup");
        }
        groupDAO.delete(t);
    }

    public List<Group> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listGroup", "insertUser")) {
            throw new PermissionException(user.getNmUser(), "listGroup");
        }
        return groupDAO.findAll();
    }

    public Group findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getGroup", "insertUser")) {
            throw new PermissionException(user.getNmUser(), "getGroup");
        }
        return groupDAO.findById(id);
    }

    public Group insert(Group t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertGroup")) {
            throw new PermissionException(user.getNmUser(), "insertGroup");
        }
        return groupDAO.insert(t);
    }

    public Group update(Group t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editGroup")) {
            throw new PermissionException(user.getNmUser(), "editGroup");
        }
        return groupDAO.update(t);
    }
}
