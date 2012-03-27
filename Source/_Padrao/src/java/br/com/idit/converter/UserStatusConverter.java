package br.com.idit.converter;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.UserStatus;
import br.com.idit.service.UserStatusService;
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status dos Usuários."));
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
