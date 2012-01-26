package br.com.icrm.service;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.dao.ProdutoImagemDAO;
import br.com.icrm.persistence.entity.ProdutoImagem;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "produtoImagemService")
public class ProdutoImagemService implements Serializable {

    private static final long serialVersionUID = 1239876645234523L;
    private ProdutoImagemDAO dao = new ProdutoImagemDAO();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProdutoImagem insert(ProdutoImagem m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "insertProdutoImagem")) {
            throw new PermissionException(user.getNmUser(), "insertProdutoImagem");
        }
        return dao.insert(m);
    }
    
    public ProdutoImagem update(ProdutoImagem m) throws ICRMException {
        if (!Security.checkPolicy(this.user, "editProdutoImagem")) {
            throw new PermissionException(user.getNmUser(), "editProdutoImagem");
        }
        return dao.update(m);
    }

    public List<ProdutoImagem> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, "listProdutoImagem")) {
            throw new PermissionException(user.getNmUser(), "listProdutoImagem");
        }
        return dao.findAll();
    }

    public void delete(ProdutoImagem c) throws ICRMException {
        if (!Security.checkPolicy(this.user, "deleteProdutoImagem")) {
            throw new PermissionException(user.getNmUser(), "deleteProdutoImagem");
        }
        dao.delete(c);
    }
}
