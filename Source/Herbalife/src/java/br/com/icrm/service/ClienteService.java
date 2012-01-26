package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.ClienteDAO;
import br.com.icrm.persistence.entity.Cliente;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "clienteService")
public class ClienteService implements Serializable {

    private static final long serialVersionUID = 23172328364123L;
    private ClienteDAO dao = new ClienteDAO();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cliente findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getCliente")) {
            throw new PermissionException(user.getNmUser(), "getCliente");
        }
        return dao.findByName(name);
    }

    public Cliente findByEmail(String email) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getCliente")) {
            throw new PermissionException(user.getNmUser(), "getCliente");
        }
        return dao.findByEmail(email);
    }

    public List<Cliente> findByNameStartsLike(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "listCliente")) {
            throw new PermissionException(user.getNmUser(), "listCliente");
        }
        return dao.findByNameStartsLike(name);
    }

    public Cliente insert(Cliente m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertCliente")) {
            throw new PermissionException(user.getNmUser(), "insertCliente");
        }
        return dao.insert(m);
    }
    
    public Cliente update(Cliente m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editCliente")) {
            throw new PermissionException(user.getNmUser(), "editCliente");
        }
        return dao.update(m);
    }

    public List<Cliente> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listCliente")) {
            throw new PermissionException(user.getNmUser(), "listCliente");
        }
        return dao.findAll();
    }

    public void delete(Cliente c) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteCliente")) {
            throw new PermissionException(user.getNmUser(), "deleteCliente");
        }
        dao.delete(c);
    }
}
