package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.converter.CategoriaConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Categoria;
import br.com.icrm.security.Session;
import br.com.icrm.service.CategoriaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import org.apache.log4j.Logger;

@ViewScoped
@ManagedBean(name = "categoriaBean")
public class CategoriaBean implements Serializable {

    private static final long serialVersionUID = 7639120347293829L;
    private static final Logger LOGGER;
    private CategoriaService service = new CategoriaService();
    private Categoria categoria = new Categoria();
    private List<Categoria> categorias = new ArrayList<Categoria>();
    private List<Categoria> orphanCategorias = new ArrayList<Categoria>();
    private boolean editando = false;

    static {
        LOGGER = Logger.getLogger(CategoriaBean.class);
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session != null) {
            if (session.getLoggedUser() != null) {
                service.setUser(session.getLoggedUser());
                try {
                    this.categorias = service.findAll();
                    this.orphanCategorias = service.findAllOrphans();
                } catch (PermissionException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Categorias."));
                    LOGGER.error(ex);
                } catch (ICRMException ex) {
                    LOGGER.error("Problema ao executar PostConstruct", ex);
                }
            }
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public List<Categoria> getOrphanCategorias() {
        return orphanCategorias;
    }

    public void setOrphanCategorias(List<Categoria> orphanCategorias) {
        this.orphanCategorias = orphanCategorias;
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (Categoria m : getCategorias()) {
            final StringBuilder nome = new StringBuilder(m.getNmCategoria());
            Categoria cat = m;
            while(cat.getPai() !=null) {
                nome.insert(0, "&nbsp;&nbsp;&nbsp;&nbsp;");
                cat = cat.getPai();
            }
            final SelectItem sitem = new SelectItem(m, nome.toString());
            sitem.setEscape(false);
            listItems.add(sitem);
        }
        return listItems;
    }

    public CategoriaConverter getConverter() {
        return new CategoriaConverter(service);
    }

    public void inserir() {
        try {
            service.insert(categoria);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para inserir uma nova Categoria."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao inserir uma nova Categoria", ex);
        }
        categoria = new Categoria();
        init();
    }

    public void editar() {
        this.editando = true;
    }

    public void confirmarEdicao() {
        try {
            service.update(categoria);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Alterar as informações de uma Categoria."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao alterar uma Categoria", ex);
        }
        this.categoria = new Categoria();
        this.editando = false;
        init();
    }

    public void excluir() {
        try {
            service.delete(categoria);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Excluir uma Categoria."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao excluir uma Categoria", ex);
        }
        categoria = new Categoria();
        init();
    }

    public void cancelarEdicao() {
        this.categoria = new Categoria();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.categoria = new Categoria();
    }

    public List<SelectItem> getTreeCategorias() {
        return getTreeCategorias(getOrphanCategorias());
    }

    private List<SelectItem> getTreeCategorias(List<Categoria> modules) {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (Categoria m : categorias) {
            SelectItemGroup g = new SelectItemGroup(m.getNmCategoria());
            if (m.getFilhos() != null && m.getFilhos().size() > 0) {
                getTreeCategorias(m.getFilhos());
            }
            listItems.add(g);
        }
        return listItems;
    }
}
