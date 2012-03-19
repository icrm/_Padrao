package br.com.idit.converter;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.Module;
import br.com.idit.service.ModuleService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class ModuleConverter implements Converter {

    private static final Logger LOGGER;
    private ModuleService service;

    static {
        LOGGER = Logger.getLogger(ModuleConverter.class);
    }

    public ModuleConverter(ModuleService service) {
        LOGGER.debug("Criando instância do Objeto ModuleConverter");
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        LOGGER.debug("Convertendo Objeto apartir do nome.");
        Module m = null;
        try {
            m = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Módulos."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao utilizar Converter", ex);
        }

        return m;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        LOGGER.debug("Convertendo Objeto apartir de sua Instância.");
        if (o != null && o instanceof Module) {
            return ((Module) o).getNmModule();
        } else {
            return null;
        }
    }
}
