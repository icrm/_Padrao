package br.com.icrm.converter;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.UserStatus;
import br.com.icrm.service.UserStatusService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class UserStatusConverter implements Converter {

    private UserStatusService service;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(UserStatusConverter.class);
    }

    public UserStatusConverter(UserStatusService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        UserStatus us = null;
        try {
            us = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Grupos."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao utilizar Converter", ex);
        }
        return us;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof UserStatus) {
            return ((UserStatus) o).getNmStatus();
        } else {
            return null;
        }
    }
}
