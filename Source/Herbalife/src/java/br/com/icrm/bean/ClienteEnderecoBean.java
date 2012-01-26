package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.ClienteEndereco;
import br.com.icrm.security.Session;
import br.com.icrm.service.ClienteEnderecoService;
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
@ManagedBean(name = "clienteEnderecoBean")
public class ClienteEnderecoBean implements Serializable {

    private static final long serialVersionUID = 47283912237493L;
    private static final Logger LOGGER;
    private ClienteEnderecoService service = new ClienteEnderecoService();
    private ClienteEndereco endereco = new ClienteEndereco();
    private List<ClienteEndereco> enderecos = new ArrayList<ClienteEndereco>();
    private boolean editando = false;

    static {
        LOGGER = Logger.getLogger(ClienteEnderecoBean.class);
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public ClienteEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(ClienteEndereco endereco) {
        this.endereco = endereco;
    }

    public List<ClienteEndereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<ClienteEndereco> enderecos) {
        this.enderecos = enderecos;
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance()
                .getELContext().getELResolver().getValue(FacesContext
                .getCurrentInstance().getELContext(), null, "accmmSession");
        if (session != null) {
            if (session.getLoggedUser() != null) {
                service.setUser(session.getLoggedUser());
                try {
                    this.enderecos = service.findAll();
                } catch (PermissionException ex) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage("Você não tem permissão para "
                                    + "visualizar as informações de "
                                    + "Endereço dos Clientes."));
                    LOGGER.error(ex);
                } catch (ICRMException ex) {
                    LOGGER.error("Problema ao executar PostConstruct", ex);
                }
            }
        }
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (ClienteEndereco ce : getEnderecos()) {
            listItems.add(new SelectItem(ce, ce.getNmEndereco()));
        }
        return listItems;
    }

    public void inserir() {
        try {
            service.insert(endereco);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new 
                    FacesMessage("Você não tem permissão para "
                    + "inserir um novo Endereco."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao inserir um novo Endereco", ex);
        }
        this.endereco = new ClienteEndereco();
        init();
    }

    public void editar() {
        this.editando = true;
    }

    public void confirmarEdicao() {
        try {
            service.update(endereco);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão para "
                            + "Alterar as informações de um Endereço."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao alterar um Endereço", ex);
        }
        this.endereco = new ClienteEndereco();
        this.editando = false;
        init();
    }

    public void excluir() {
        try {
            service.delete(endereco);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão para "
                            + "Excluir um Endereço."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao excluir um Endereço", ex);
        }
        endereco = new ClienteEndereco();
        init();
    }

    public void cancelarEdicao() {
        this.endereco = new ClienteEndereco();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.endereco = new ClienteEndereco();
    }
}
