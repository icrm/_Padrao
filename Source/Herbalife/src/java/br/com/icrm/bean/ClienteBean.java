package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.converter.ClienteConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Cliente;
import br.com.icrm.security.Session;
import br.com.icrm.service.ClienteService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ViewScoped
@ManagedBean(name = "clienteBean")
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 3882912389832012L;
    private static final Logger LOGGER;
    private ClienteService service = new ClienteService();
    private Cliente cliente = new Cliente();
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private boolean editando = false;

    static {
        LOGGER = Logger.getLogger(ClienteBean.class);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance()
                .getELContext(), null, "accmmSession");
        if (session != null) {
            if (session.getLoggedUser() != null) {
                service.setUser(session.getLoggedUser());
                try {
                    this.clientes = service.findAll();
                } catch (PermissionException ex) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage("Você não tem permissão "
                                    + "para visualizar as informações "
                                    + "de Clientes."));
                    LOGGER.error(ex);
                } catch (ICRMException ex) {
                    LOGGER.error("Problema ao executar PostConstruct", ex);
                }
            }
        }
    }
    
    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (Cliente m : getClientes()) {
            listItems.add(new SelectItem(m, m.getNmCliente()));
        }
        return listItems;
    }

    public void inserir() {
        try {
            service.insert(cliente);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para "
                            + "inserir um novo Cliente."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao inserir um novo Cliente", ex);
        }
        cliente = new Cliente();
        init();
    }

    public void editar() {
        this.editando = true;
    }

    public void confirmarEdicao() {
        try {
            service.update(cliente);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para "
                            + "Alterar as informações de um Cliente."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao alterar um Cliente", ex);
        }
        this.cliente = new Cliente();
        this.editando = false;
        init();
    }

    public void excluir() {
        try {
            service.delete(cliente);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para "
                            + "Excluir um Cliente."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao excluir um Cliente", ex);
        }
        cliente = new Cliente();
        init();
    }

    public void cancelarEdicao() {
        this.cliente = new Cliente();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.cliente = new Cliente();
    }
}
