package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.ProdutoDAO;
import br.com.icrm.persistence.entity.Produto;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "produtoService")
public class ProdutoService implements Serializable {
    
    private static final long serialVersionUID = 36471947712399L;
    private ProdutoDAO dao = new ProdutoDAO();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Produto findByName(String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getProduto")) {
            throw new PermissionException(user.getNmUser(), "getProduto");
        }
        return dao.findByName(name);
    }

    public Produto insert(Produto p) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertProduto")) {
            throw new PermissionException(user.getNmUser(), "insertProduto");
        }
        return dao.insert(p);
    }

    public void delete(Produto p) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteProduto")) {
            throw new PermissionException(user.getNmUser(), "deleteProduto");
        }
        dao.delete(p);
    }

    public List<Produto> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listProduto")) {
            throw new PermissionException(user.getNmUser(), "listProduto");
        }
        return dao.findAll();
    }

    public Produto findById(Object id) throws ICRMException {
        if (!Security.checkPolicy(this.user, "getProduto")) {
            throw new PermissionException(user.getNmUser(), "getProduto");
        }
        return dao.findById(id);
    }

    public Produto update(Produto t) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editProduto")) {
            throw new PermissionException(user.getNmUser(), "editProduto");
        }
        return dao.update(t);
    }
}
