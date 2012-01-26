package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.VendaDAO;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.persistence.entity.Venda;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "vendaService")
public class VendaService implements Serializable {
    private static final long serialVersionUID = 1474392378283L;
    private VendaDAO dao = new VendaDAO();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Venda insert(Venda m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertVenda")) {
            throw new PermissionException(user.getNmUser(), "insertVenda");
        }
        return dao.insert(m);
    }
    
    public Venda update(Venda m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editVenda")) {
            throw new PermissionException(user.getNmUser(), "editVenda");
        }
        return dao.update(m);
    }

    public List<Venda> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listVenda")) {
            throw new PermissionException(user.getNmUser(), "listVenda");
        }
        return dao.findAll();
    }

    public void delete(Venda c) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteVenda")) {
            throw new PermissionException(user.getNmUser(), "deleteVenda");
        }
        dao.delete(c);
    }
}
