package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.converter.CategoriaStatusConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.CategoriaStatus;
import br.com.icrm.security.Session;
import br.com.icrm.service.CategoriaStatusService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@RequestScoped
@ManagedBean(name = "categoriaStatusBean")
public class CategoriaStatusBean {
    
    private static final long serialVersionUID = 5180256382912830L;
    private static final Logger LOGGER;
    private CategoriaStatusService service = new CategoriaStatusService();
    private CategoriaStatus categoriaStatus = new CategoriaStatus();
    private List<CategoriaStatus> categoriasStatus = new ArrayList<CategoriaStatus>();
    
    static {
        LOGGER = Logger.getLogger(CategoriaStatusBean.class);
    }
    
    public CategoriaStatusBean() {
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                this.categoriasStatus = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status das Categorias."));
                LOGGER.error(ex);
            } catch (ICRMException ex) {
                LOGGER.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    public CategoriaStatus getCategoriaStatus() {
        return categoriaStatus;
    }

    public void setCategoriaStatus(CategoriaStatus categoriaStatus) {
        this.categoriaStatus = categoriaStatus;
    }

    public List<CategoriaStatus> getCategoriasStatus() {
        return categoriasStatus;
    }

    public void setCategoriasStatus(List<CategoriaStatus> categoriasStatus) {
        this.categoriasStatus = categoriasStatus;
    }
    
    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (CategoriaStatus ms : getCategoriasStatus()) {
            listItems.add(new SelectItem(ms, ms.getNmStatus()));
        }
        return listItems;
    }

    public CategoriaStatusConverter getConverter() {
        return new CategoriaStatusConverter(service);
    }
}
