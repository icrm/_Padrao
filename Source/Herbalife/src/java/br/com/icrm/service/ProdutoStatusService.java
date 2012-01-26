package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.ProdutoStatusDAO;
import br.com.icrm.persistence.entity.ProdutoStatus;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "produtoStatusService")
public class ProdutoStatusService implements Serializable {

    private static final long serialVersionUID = 3123947561231723L;
    private ProdutoStatusDAO dao = new ProdutoStatusDAO();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public ProdutoStatus findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getProdutoStatus")) {
            throw new PermissionException(user.getNmUser(), "getProdutoStatus");
        }
        return dao.findByName(name);
    }

    public void delete(ProdutoStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteProdutoStatus")) {
            throw new PermissionException(user.getNmUser(), "deleteProdutoStatus");
        }
        dao.delete(t);
    }

    public List<ProdutoStatus> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listProdutoStatus")) {
            throw new PermissionException(user.getNmUser(), "listProdutoStatus");
        }
        return dao.findAll();
    }

    public ProdutoStatus findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getProdutoStatus")) {
            throw new PermissionException(user.getNmUser(), "getProdutoStatus");
        }
        return dao.findById(id);
    }

    public ProdutoStatus insert(ProdutoStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertProdutoStatus")) {
            throw new PermissionException(user.getNmUser(), "insertProdutoStatus");
        }
        return dao.insert(t);
    }

    public ProdutoStatus update(ProdutoStatus t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editProdutoStatus")) {
            throw new PermissionException(user.getNmUser(), "editProdutoStatus");
        }
        return dao.update(t);
    }
}
