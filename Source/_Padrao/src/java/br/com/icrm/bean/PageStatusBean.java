package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.converter.PageStatusConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.PageStatus;
import br.com.icrm.security.Session;
import br.com.icrm.service.PageStatusService;
import java.io.Serializable;
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
@ManagedBean(name = "pageStatusBean")
public class PageStatusBean implements Serializable {

    //<editor-fold desc="ATTRIBUTES">
    private static final long serialVersionUID = -1;
    private static final Logger logger;
    private PageStatusService service = new PageStatusService();
    private PageStatus pageStatus = new PageStatus();
    private List<PageStatus> pagesStatus = new ArrayList<PageStatus>();
    //</editor-fold>

    static {
        logger = Logger.getLogger(PageStatusBean.class);
    }

    public PageStatusBean() {
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                this.pagesStatus = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status das Páginas."));
                logger.error(ex);
            } catch (ICRMException ex) {
                logger.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    //<editor-fold desc="GETTERS AND SETTER">
    public PageStatus getPageStatus() {
        return pageStatus;
    }

    public void setPageStatus(PageStatus pageStatus) {
        this.pageStatus = pageStatus;
    }

    public List<PageStatus> getPagesStatus() {
        return pagesStatus;
    }

    public void setPagesStatus(List<PageStatus> pagesStatus) {
        this.pagesStatus = pagesStatus;
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (PageStatus ps : getPagesStatus()) {
            listItems.add(new SelectItem(ps, ps.getNmStatus()));
        }
        return listItems;
    }
    //</editor-fold>

    public PageStatusConverter getConverter() {
        return new PageStatusConverter(service);
    }
}