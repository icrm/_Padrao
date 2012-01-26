package br.com.icrm.converter;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.ProdutoStatus;
import br.com.icrm.service.ProdutoStatusService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class ProdutoStatusConverter implements Converter {

    private static final Logger LOGGER;
    private ProdutoStatusService service;

    static {
        LOGGER = Logger.getLogger(ProdutoStatusConverter.class);
    }

    public ProdutoStatusConverter(ProdutoStatusService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        ProdutoStatus pStatus = null;
        try {
            pStatus = service.findByName(string);
        }  catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para visualizar "
                            + "as informações de Status das Páginas."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao utilizar Converter", ex);
        }
        return pStatus;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof ProdutoStatus) {
            return ((ProdutoStatus) o).getNmStatus();
        } else {
            return null;
        }
    }
}
