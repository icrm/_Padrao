package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.bean.picklist.PolicyPickListBean;
import br.com.icrm.bean.tree.PageTreeBean;
import br.com.icrm.converter.GroupConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Group;
import br.com.icrm.persistence.entity.Page;
import br.com.icrm.persistence.entity.Policy;
import br.com.icrm.security.Session;
import br.com.icrm.service.GroupService;
import br.com.icrm.service.PolicyService;
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
import org.primefaces.model.TreeNode;

@ManagedBean(name = "groupBean")
@ViewScoped
public class GroupBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="ATTRIBUTES">
    private static final long serialVersionUID = -1L;
    private static final Logger logger;
    private GroupService service = new GroupService();
    private PolicyService pService = new PolicyService();
    private Group group = new Group();
    private List<Group> groups = new ArrayList<Group>();
    private List<Policy> policies = new ArrayList<Policy>();
    private boolean editando = false;
    //</editor-fold>

    static {
        logger = Logger.getLogger(GroupBean.class);
    }

    public GroupBean() {
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            pService.setUser(session.getLoggedUser());
            try {
                this.groups = service.findAll();
                this.policies = pService.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Grupos."));
                logger.error(ex);
            } catch (ICRMException ex) {
                logger.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (Group g : getGroups()) {
            listItems.add(new SelectItem(g, g.getNmGroup()));
        }
        return listItems;
    }
    //</editor-fold>

    public GroupConverter getConverter() {
        return new GroupConverter(service);
    }

    public void inserir() {
        PageTreeBean ptb = (PageTreeBean) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "pageTreeBean");
        for (TreeNode tn : ptb.getSelectedNodes()) {
            if (tn.getData() instanceof Page) {
                if (!group.getPages().contains((Page) tn.getData())) {
                    group.getPages().add((Page) tn.getData());
                }
            }
        }

        PolicyPickListBean pplb = (PolicyPickListBean) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "policyPickListBean");
        for (Policy p : pplb.getPolicies().getTarget()) {
            if (!group.getPolicies().contains(p)) {
                group.getPolicies().add(p);
            }
        }

        try {
            service.insert(group);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Insrir um novo Grupo."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao inserir um novo Grupo", ex);
        }

        group = new Group();
        pplb.getPolicies().getTarget().clear();
        pplb.getPolicies().getSource().clear();
        pplb.getPolicies().getSource().addAll(this.policies);
        ptb.clearSelection();
        init();
    }

    public void editar() {
        this.editando = true;

        PolicyPickListBean pplb = (PolicyPickListBean) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "policyPickListBean");
        pplb.getPolicies().getTarget().addAll(group.getPolicies());
        pplb.getPolicies().getSource().removeAll(group.getPolicies());

        PageTreeBean ptb = (PageTreeBean) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "pageTreeBean");
        ptb.defineSelected(group.getPages());

    }

    public void confirmarEdicao() {
        group.getPages().clear();
        group.getPolicies().clear();
        PageTreeBean ptb = (PageTreeBean) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "pageTreeBean");
        for (TreeNode tn : ptb.getSelectedNodes()) {
            if (tn.getData() instanceof Page) {
                if (!group.getPages().contains((Page) tn.getData())) {
                    group.getPages().add((Page) tn.getData());
                }
            }
        }

        PolicyPickListBean pplb = (PolicyPickListBean) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "policyPickListBean");
        for (Policy p : pplb.getPolicies().getTarget()) {
            if (!group.getPolicies().contains(p)) {
                group.getPolicies().add(p);
            }
        }

        try {
            service.update(group);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Editar um Grupo."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao alterar um Grupo", ex);
        }
        this.editando = false;
        this.group = new Group();
        pplb.getPolicies().getTarget().clear();
        pplb.getPolicies().getSource().clear();
        pplb.getPolicies().getSource().addAll(this.policies);
        ptb.clearSelection();
        init();
    }

    public void excluir() {
        try {
            service.delete(group);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Excluir um Grupo."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao excluir um Grupo", ex);
        }
        group = new Group();
        init();
    }

    public void cancelarEdicao() {
        this.group = new Group();
        this.editando = false;

        PolicyPickListBean pplb = (PolicyPickListBean) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "policyPickListBean");
        pplb.getPolicies().getTarget().clear();
        pplb.getPolicies().getSource().clear();
        pplb.getPolicies().getSource().addAll(this.policies);
        init();
    }

    public void cancelarExclusao() {
        this.group = new Group();
    }
}
