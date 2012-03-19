package br.com.idit.converter;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.ModuleStatus;
import br.com.idit.service.ModuleStatusService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class ModuleStatusConverter implements Converter {

    private static final Logger logger;
    private ModuleStatusService service;

    static {
        logger = Logger.getLogger(ModuleStatusConverter.class);
    }

    public ModuleStatusConverter(ModuleStatusService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        ModuleStatus ms = null;
        try {
            ms = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status dos Módulos."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao utilizar Converter", ex);
        }
        return ms;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof ModuleStatus) {
            return ((ModuleStatus) o).getNmStatus();
        } else {
            return null;
        }
    }
}
