package br.com.icrm.bean.autocomplete;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Cliente;
import br.com.icrm.security.Session;
import br.com.icrm.service.ClienteService;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@RequestScoped
@ManagedBean(name = "clienteAutoComplete")
public class ClienteAutoComplete {

    private static final Logger LOGGER;
    private Cliente cliente;
    private ClienteService service;

    static {
        LOGGER = Logger.getLogger(ClienteAutoComplete.class);
    }

    public ClienteAutoComplete() {
        Session session = (Session) FacesContext.getCurrentInstance()
                .getELContext().getELResolver().getValue(FacesContext
                .getCurrentInstance().getELContext(), null, "accmmSession");
        this.service = new ClienteService();
        this.service.setUser(session.getLoggedUser());
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> completeCliente(String query) {
        List<Cliente> suggestion = new ArrayList<Cliente>();

        try {
            suggestion.addAll(service.findByNameStartsLike(query));
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para visualizar "
                    + "as informações de Clientes."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao executar CompleteAutoCliente", ex);
        }
        return suggestion;
    }
}
