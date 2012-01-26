package br.com.icrm.converter;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Categoria;
import br.com.icrm.service.CategoriaService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class CategoriaConverter implements Converter {
    
    private static final Logger LOGGER;
    private CategoriaService service;
    
    static {
        LOGGER = Logger.getLogger(CategoriaConverter.class);
    }
    
    public CategoriaConverter(CategoriaService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Categoria m = null;
        try {
            m = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Categorias."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao utilizar Converter", ex);
        }

        return m;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof Categoria) {
            return ((Categoria) o).getNmCategoria();
        } else {
            return null;
        }
    }
}
