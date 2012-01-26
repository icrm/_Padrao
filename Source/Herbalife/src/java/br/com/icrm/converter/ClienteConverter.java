package br.com.icrm.converter;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Cliente;
import br.com.icrm.security.Session;
import br.com.icrm.service.ClienteService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.Logger;

@FacesConverter(value="clienteConverter")
public class ClienteConverter implements Converter {

    private static final Logger LOGGER;
    private ClienteService service;

    static {
        LOGGER = Logger.getLogger(ClienteConverter.class);
    }

    public ClienteConverter() {
        Session session = (Session) FacesContext.getCurrentInstance()
                .getELContext().getELResolver().getValue(FacesContext
                .getCurrentInstance().getELContext(), null, "accmmSession");
        this.service = new ClienteService();
        this.service.setUser(session.getLoggedUser());
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Cliente c = null;
        try {
            c =  service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão para "
                            + "visualizar as informações de Clientes."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao utilizar Converter", ex);
        }
        return c;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof Cliente) {
            return ((Cliente) o).getNmCliente();
        } else {
            return null;
        }
    }
    
}
