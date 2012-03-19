package br.com.idit.converter;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.Group;
import br.com.idit.service.GroupService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class GroupConverter implements Converter {

    private static final Logger logger;
    private GroupService service;

    static {
        logger = Logger.getLogger(GroupConverter.class);
    }

    public GroupConverter(GroupService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Group g = null;
        try {
            g = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Grupos."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao utilizar Converter", ex);
        }
        return g;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof Group) {
            return ((Group) o).getNmGroup();
        } else {
            return null;
        }
    }
}
