package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Venda;
import br.com.icrm.security.Session;
import br.com.icrm.service.VendaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ViewScoped
@ManagedBean(name = "vendaBean")
public class VendaBean implements Serializable {
    private static final long serialVersionUID = 342139874367234L;
    private static final Logger LOGGER;
    private VendaService service = new VendaService();
    private Venda venda = new Venda();
    private List<Venda> vendas = new ArrayList<Venda>();
    private boolean editando = false;

    static {
        LOGGER = Logger.getLogger(VendaBean.class);
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
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
                .getELContext().getELResolver().getValue(FacesContext
                .getCurrentInstance().getELContext(), null, "accmmSession");
        if (session != null) {
            if (session.getLoggedUser() != null) {
                service.setUser(session.getLoggedUser());
                try {
                    this.vendas = service.findAll();
                } catch (PermissionException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, 
                            new FacesMessage("Você não tem permissão para "
                                    + "visualizar as informações de Vendas."));
                    LOGGER.error(ex);
                } catch (ICRMException ex) {
                    LOGGER.error("Problema ao executar PostConstruct", ex);
                }
            }
        }
    }

    public void inserir() {
        try {
            service.insert(venda);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão para "
                            + "inserir uma nova Venda."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao inserir uma nova Venda", ex);
        }
        venda = new Venda();
        init();
    }

    public void editar() {
        this.editando = true;
    }

    public void confirmarEdicao() {
        try {
            service.update(venda);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão para Alterar "
                            + "as informações de uma Venda."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao alterar uma Venda", ex);
        }
        this.venda = new Venda();
        this.editando = false;
        init();
    }

    public void excluir() {
        try {
            service.delete(venda);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão para Excluir "
                            + "uma Venda."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao excluir uma Venda", ex);
        }
        venda = new Venda();
        init();
    }

    public void cancelarEdicao() {
        this.venda = new Venda();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.venda = new Venda();
    }

}
