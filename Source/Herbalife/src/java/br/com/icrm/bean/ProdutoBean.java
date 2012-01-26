package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.converter.ProdutoConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Produto;
import br.com.icrm.security.Session;
import br.com.icrm.service.ProdutoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ManagedBean(name = "produtoBean")
@ViewScoped
public class ProdutoBean implements Serializable {
    
    private static final long serialVersionUID = 421234873472823L;
    private static final Logger LOGGER;
    private ProdutoService service = new ProdutoService();
    private Produto produto = new Produto();
    private List<Produto> produtos = new ArrayList<Produto>();
    private boolean editando = false;
    private String descricaoCompleta;
    
    static {
        LOGGER = Logger.getLogger(ProdutoBean.class);
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getDescricaoCompleta() {
        return descricaoCompleta;
    }

    public void setDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = descricaoCompleta;
    }
    
    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance()
                .getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                produtos = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Você não tem permissão para "
                                + "visualizar as informações de Produtos."));
            } catch (ICRMException ex) {
                LOGGER.error("Problema ao visualizar Produtos", ex);
            }
        }
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (Produto p : getProdutos()) {
            listItems.add(new SelectItem(p, p.getNmProduto()));
        }
        return listItems;
    }

    public ProdutoConverter getCovnerter() {
        return new ProdutoConverter(service);
    }

    public void inserir() {
        try {
            produto.setDsProdutoLonga(this.descricaoCompleta.getBytes());
            service.insert(produto);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para "
                            + "Inserir um novo Produto."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao inserir um novo Produto", ex);
        }
        produto = new Produto();
        this.descricaoCompleta = null;
        init();
    }

    public void editar() {
        this.editando = true;
    }
    
    public void confirmarEdicao() {
        try {
            produto.setDsProdutoLonga(this.descricaoCompleta.getBytes());
            service.update(produto);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão para "
                            + "Alterar as informações de um Produto."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao alterar um Produto", ex);
        }
        this.produto = new Produto();
        this.editando = false;
        this.descricaoCompleta = null;
        init();
    }

    public void excluir() {
        try {
            service.delete(produto);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão "
                            + "para Excluir um Produto."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao excluir um Produto", ex);
        }
        this.produto = new Produto();
        init();
    }

    public void cancelarEdicao() {
        this.produto = new Produto();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.produto = new Produto();
    }

}
