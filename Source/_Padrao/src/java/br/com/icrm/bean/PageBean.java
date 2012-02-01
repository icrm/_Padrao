package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.converter.PageConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Module;
import br.com.icrm.persistence.entity.Page;
import br.com.icrm.security.Session;
import br.com.icrm.service.PageService;
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

@ManagedBean(name = "pageBean")
@ViewScoped
public class PageBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger logger;
    private PageService service = new PageService();
    private Page page = new Page();
    private Module module;
    private List<Page> pages = new ArrayList<Page>();
    private List<Page> pagesByModule = new ArrayList<Page>();
    private boolean fgMain;
    private boolean fgShowMenu;
    private boolean editando = false;

    static {
        logger = Logger.getLogger(PageBean.class);
    }

    public PageBean() {
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                pages = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Páginas."));
            } catch (ICRMException ex) {
                logger.error("Problema ao visualizar Páginas", ex);
            }
        }
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public boolean isFgMain() {
        return fgMain;
    }

    public void setFgMain(boolean fgMain) {
        this.fgMain = fgMain;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public boolean isFgShowMenu() {
        return fgShowMenu;
    }

    public void setFgShowMenu(boolean fgShowMenu) {
        this.fgShowMenu = fgShowMenu;
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (Page p : getPages()) {
            listItems.add(new SelectItem(p, p.getNmPage()));
        }
        return listItems;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public PageConverter getConverter() {
        return new PageConverter(service);
    }

    public void inserir() {
        page.setFgMain((byte) ((fgMain) ? 1 : 0));
        page.setFgShowMenu((byte) ((fgShowMenu) ? 0 : 1)); // Se fgShowMenu for verdadeiro então a página não será mostrada no menu
        try {
            service.insert(page);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Inserir uma nova Página."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao inserir uma nova Página", ex);
        }
        this.page = new Page();
        this.fgMain = false;
        this.fgShowMenu = false;
        init();
    }

    public void editar() {
        this.editando = true;
    }

    public void confirmarEdicao() {
        this.page.setFgMain((byte) ((fgMain) ? 1 : 0));
        this.page.setFgShowMenu((byte) ((fgShowMenu) ? 0 : 1)); // Se fgShowMenu for verdadeiro então a página não será mostrada no menu
        try {
            this.service.update(page);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Alterar as informações de uma Página."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao alterar uma Página", ex);
        }
        this.page = new Page();
        this.editando = false;
        this.fgMain = false;
        this.fgShowMenu = false;
        init();
    }

    public void excluir() {
        try {
            service.delete(page);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Excluir uma Página."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao excluir uma Página", ex);
        }
        page = new Page();
        this.editando = false;
        init();
    }

    public void cancelarEdicao() {
        this.page = new Page();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.page = new Page();
    }

    public List<Page> getPagesByModule() {
        return this.pagesByModule;
    }

    public void updatePagesByModule() {
        if (module != null) {
            try {
                this.pagesByModule = service.findByModule(module);
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Páginas."));
                logger.error(ex);
            } catch (ICRMException ex) {
                logger.error("Problema ao visualizar Páginas", ex);
            }
        }
    }
}
