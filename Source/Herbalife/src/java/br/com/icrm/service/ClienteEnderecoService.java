package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.ClienteEnderecoDAO;
import br.com.icrm.persistence.entity.ClienteEndereco;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "clienteEnderecoService")
public class ClienteEnderecoService implements Serializable {

    private static final long serialVersionUID = 7873847232874833L;
    private ClienteEnderecoDAO dao = new ClienteEnderecoDAO();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public ClienteEndereco findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getClienteEndereco")) {
            throw new PermissionException(user.getNmUser(), "getClienteEndereco");
        }
        return dao.findByName(name);
    }

    public ClienteEndereco insert(ClienteEndereco m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertClienteEndereco")) {
            throw new PermissionException(user.getNmUser(), "insertClienteEndereco");
        }
        return dao.insert(m);
    }
    
    public ClienteEndereco update(ClienteEndereco m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editClienteEndereco")) {
            throw new PermissionException(user.getNmUser(), "editClienteEndereco");
        }
        return dao.update(m);
    }

    public List<ClienteEndereco> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listClienteEndereco")) {
            throw new PermissionException(user.getNmUser(), "listClienteEndereco");
        }
        return dao.findAll();
    }

    public void delete(ClienteEndereco c) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteClienteEndereco")) {
            throw new PermissionException(user.getNmUser(), "deleteClienteEndereco");
        }
        dao.delete(c);
    }
}