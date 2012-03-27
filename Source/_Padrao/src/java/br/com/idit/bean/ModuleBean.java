package br.com.idit.bean;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.converter.ModuleConverter;
import br.com.idit.datamodel.ModuleDataModel;
import br.com.idit.exception.ChildException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.Module;
import br.com.idit.security.Session;
import br.com.idit.service.ModuleService;
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

@ManagedBean(name = "moduleBean")
@ViewScoped
public class ModuleBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger logger;
    private ModuleService service = new ModuleService();
    private Module module = new Module();
    private List<Module> modules = new ArrayList<Module>();
    private List<Module> orphanModules = new ArrayList<Module>();
    private List<SelectItem> orderedListItems = new ArrayList<SelectItem>();
    private Module[] selectedModules;
    private boolean editando = false;

    static {
        logger = Logger.getLogger(ModuleBean.class);
    }

    public ModuleBean() {
    }

    public Module[] getSelectedModules() {
        return selectedModules;
    }

    public void setSelectedModules(Module[] selectedModules) {
        this.selectedModules = selectedModules;
    }

    @PostConstruct
    public void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session != null) {
            if (session.getLoggedUser() != null) {
                service.setUser(session.getLoggedUser());
                try {
                    this.modules = service.findAll();
                    this.orphanModules = service.findAllOrphans();
                } catch (PermissionException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Módulos."));
                    logger.error(ex);
                } catch (ICRMException ex) {
                    logger.error("Problema ao executar PostConstruct", ex);
                }
            }
        }
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Module> getOrphanModules() {
        return orphanModules;
    }

    public void setOrphanModules(List<Module> orphanModules) {
        this.orphanModules = orphanModules;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public List<SelectItem> getListItems() {
        final List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (Module m : getModules()) {
            final StringBuilder nome = new StringBuilder(m.getNmModule());
            Module mod = m;
            while (mod.getParent() != null) {
                nome.insert(0, "&nbsp;&nbsp;&nbsp;&nbsp;");
                mod = mod.getParent();
            }
            final SelectItem sitem = new SelectItem(m, nome.toString());
            sitem.setEscape(false);
            listItems.add(sitem);
        }
        return listItems;
    }

    public ModuleConverter getConverter() {
        return new ModuleConverter(service);
    }

    public void inserir() {
        logger.debug("Iniciando execução do Bean para inserir o Objeto");
        boolean success = true;
        try {
            logger.debug("Executando método de Inserção do Service");
            service.insert(module);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para inserir um novo Módulo."));
            logger.error(ex);
            success = false;
        } catch (ICRMException ex) {
            logger.error("Problema ao inserir um novo Módulo", ex);
            success = false;
        }
        if (success) {
            FacesMessage msg = new FacesMessage("Módulo Inserido com Sucesso!");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        module = new Module();
        init();
    }

    public void editar() {
        this.editando = true;
        this.getModules().remove(this.module);
    }

    public void confirmarEdicao() {
        logger.debug("Iniciando execução do Bean para Atualizar o Objeto");
        try {
            logger.debug("Executando método de Atualização do Service");
            service.update(module);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Alterar as informações de um Módulo."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao alterar um Módulo", ex);
        }
        this.module = new Module();
        this.editando = false;
        init();
    }

    public void excluir() {
        boolean success = true;
        try {
            service.delete(module);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Excluir um Módulo."));
            logger.error(ex);
            success = false;
        } catch (ChildException ex) {
            FacesMessage msg = new FacesMessage(ex.getMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            success = false;
        } catch (ICRMException ex) {
            logger.error("Problema ao excluir um Módulo", ex);
            success = false;
        }
        if (success) {
            FacesMessage msg = new FacesMessage("Registro excluído com Sucesso!");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        module = new Module();
        this.editando = false;
        init();
    }

    public void cancelarEdicao() {
        this.module = new Module();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.module = new Module();
    }

    public List<SelectItem> getOrderedListItems() {
        orderedListItems.clear();
        return getOrderedListItems(getOrphanModules(), 0);
    }

    private List<SelectItem> getOrderedListItems(final List<Module> mods,
            final int level) {
        final StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < level; i++) {
            prefix.append("&nbsp;");
        }

        for (Module m : mods) {
            SelectItem g = new SelectItem(m, prefix.toString() + m.getNmModule());
            g.setEscape(false);
            orderedListItems.add(g);
            if (m.getChildren() != null && m.getChildren().size() > 0) {
                getOrderedListItems(m.getChildren(), level + 4);
            }
        }
        return orderedListItems;
    }

    public ModuleDataModel getDataModel() {
        return new ModuleDataModel(getModules());
    }
}
