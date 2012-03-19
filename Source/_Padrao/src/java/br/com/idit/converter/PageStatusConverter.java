package br.com.idit.converter;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.PageStatus;
import br.com.idit.service.PageStatusService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class PageStatusConverter implements Converter {

    private static final Logger logger;
    private PageStatusService service;

    static {
        logger = Logger.getLogger(PageStatusConverter.class);
    }

    public PageStatusConverter(PageStatusService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        PageStatus ps = null;
        try {
            ps = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status das Páginas."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao utilizar Converter", ex);
        }
        return ps;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof PageStatus) {
            return ((PageStatus) o).getNmStatus();
        } else {
            return null;
        }
    }
}
