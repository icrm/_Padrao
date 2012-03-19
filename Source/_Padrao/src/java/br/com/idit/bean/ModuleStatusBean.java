package br.com.idit.bean;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.converter.ModuleStatusConverter;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.Module;
import br.com.idit.persistence.entity.ModuleStatus;
import br.com.idit.security.Session;
import br.com.idit.service.ModuleStatusService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ViewScoped
@ManagedBean(name = "moduleStatusBean")
public class ModuleStatusBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger logger;
    private ModuleStatusService service = new ModuleStatusService();
    private ModuleStatus moduleStatus = new ModuleStatus();
    private List<ModuleStatus> modulesStatus = new ArrayList<ModuleStatus>();

    static {
        logger = Logger.getLogger(ModuleStatusBean.class);
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                this.modulesStatus = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status dos Módulos."));
                logger.error(ex);
            } catch (ICRMException ex) {
                logger.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    public ModuleStatus getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(ModuleStatus moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    public List<ModuleStatus> getModulesStatus() {
        return modulesStatus;
    }

    public void setModulesStatus(List<ModuleStatus> modulesStatus) {
        this.modulesStatus = modulesStatus;
    }

    public void removeActive(ValueChangeEvent event) {
        Module module = (Module) event.getNewValue();
        if (module.getStatus().getNmStatus().equals("Inativo")) {
            try {
                this.getModulesStatus().remove(service.findByName("Ativo"));
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status dos Módulos."));
                logger.error(ex);
            } catch (ICRMException ex) {
                logger.error("Problema ao executar removeActive", ex);
            }
        } else {
            init();
        }
    }

    public List<SelectItem> getListItems() {
        final List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (ModuleStatus ms : getModulesStatus()) {
            listItems.add(new SelectItem(ms, ms.getNmStatus()));
        }
        return listItems;
    }

    public ModuleStatusConverter getConverter() {
        return new ModuleStatusConverter(service);
    }
}
