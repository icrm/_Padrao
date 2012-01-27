package br.com.icrm.converter;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Policy;
import br.com.icrm.service.PolicyService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class PolicyConverter implements Converter {

    private static final Logger logger;
    private PolicyService service;

    static {
        logger = Logger.getLogger(PolicyConverter.class);
    }

    public PolicyConverter(PolicyService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Policy p = null;
        try {
            p = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Diretivas."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao utilizar Converter", ex);
        }
        return p;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof Policy) {
            return ((Policy) o).getNmPolicy();
        } else {
            return null;
        }
    }
}
