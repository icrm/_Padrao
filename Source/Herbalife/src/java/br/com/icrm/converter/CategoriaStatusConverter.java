package br.com.icrm.converter;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.CategoriaStatus;
import br.com.icrm.service.CategoriaStatusService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class CategoriaStatusConverter implements Converter {

    private static final Logger LOGGER;
    private CategoriaStatusService service;

    static {
        LOGGER = Logger.getLogger(CategoriaStatusConverter.class);
    }

    public CategoriaStatusConverter(CategoriaStatusService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        CategoriaStatus ms = null;
        try {
            ms = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status das Categorias."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao utilizar Converter", ex);
        }
        return ms;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof CategoriaStatus) {
            return ((CategoriaStatus) o).getNmStatus();
        } else {
            return null;
        }
    }
}
