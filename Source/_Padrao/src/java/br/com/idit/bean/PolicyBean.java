package br.com.idit.bean;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.converter.PolicyConverter;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.Policy;
import br.com.idit.security.Session;
import br.com.idit.service.PolicyService;
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

@ManagedBean(name = "policyBean")
@ViewScoped
public class PolicyBean implements Serializable {

    // ATTRIBUTES
    //<editor-fold>
    private static final long serialVersionUID = -1L;
    private static final Logger logger;
    private PolicyService service = new PolicyService();
    private Policy policy = new Policy();
    private List<Policy> policies = new ArrayList<Policy>();
    private boolean editando = false;
    //</editor-fold>

    static {
        logger = Logger.getLogger(PolicyBean.class);
    }

    public PolicyBean() {
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                this.policies = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Diretivas."));
                logger.error(ex);
            } catch (ICRMException ex) {
                logger.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    // GETTERS AND SETTERS
    //<editor-fold>
    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (Policy p : getPolicies()) {
            listItems.add(new SelectItem(p, p.getNmPolicy()));
        }
        return listItems;
    }
    //</editor-fold>

    public PolicyConverter getConverter() {
        return new PolicyConverter(service);
    }

    // ACTIONS
    //<editor-fold>
    public void inserir() {
        try {
            service.insert(policy);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para inserir uma nova Diretiva."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao inserir uma nova Diretiva.", ex);
        }
        policy = new Policy();
        init();
    }

    public void editar() {
        this.editando = true;
    }

    public void confirmarEdicao() {
        try {
            service.update(policy);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Alterar as informações de uma Diretiva."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao alterar uma Diretiva.", ex);
        }
        this.policy = new Policy();
        this.editando = false;
        init();
    }

    public void excluir() {
        try {
            service.delete(policy);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Excluir uma Diretiva."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao excluir uma Diretiva.", ex);
        }
        policy = new Policy();
        this.editando = false;
        init();
    }

    public void cancelarEdicao() {
        this.policy = new Policy();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.policy = new Policy();
    }
    //</editor-fold>
}
