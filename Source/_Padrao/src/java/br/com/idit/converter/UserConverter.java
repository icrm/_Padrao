package br.com.idit.converter;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.User;
import br.com.idit.service.UserService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class UserConverter implements Converter {

    private static final Logger logger;
    private UserService service;

    static {
        logger = Logger.getLogger(UserConverter.class);
    }

    public UserConverter(UserService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        User u = null;
        try {
            u = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Usuários."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao utilizar Converter", ex);
        }
        return u;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof User) {
            return ((User) o).getNmUser();
        } else {
            return null;
        }
    }
}
