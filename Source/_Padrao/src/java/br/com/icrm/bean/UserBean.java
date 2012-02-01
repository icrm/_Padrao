package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.converter.UserConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Session;
import br.com.icrm.service.UserService;
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

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {

    //<editor-fold desc="ATTRIBUTES">
    private static final long serialVersionUID = -1L;
    private static final Logger logger;
    private UserService service = new UserService();
    private User user = new User();
    private List<User> users = new ArrayList<User>();
    private boolean editando = false;
    //</editor-fold>

    static {
        logger = Logger.getLogger(UserBean.class);
    }

    public UserBean() {
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                users = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Usuário."));
                logger.error(ex);
            } catch (ICRMException ex) {
                logger.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    //<editor-fold desc="GETTERS AND SETTERS">
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (User u : getUsers()) {
            listItems.add(new SelectItem(u, u.getNmUser()));
        }
        return listItems;
    }

    public UserConverter getConverter() {
        return new UserConverter(service);
    }

    public void inserir() {
        try {
            service.insert(user);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para "
                            + "Incluir Usuário."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao inserir um novo Usuário.", ex);
        }
        user = new User();
        init();
    }

    public void editar() {
        this.editando = true;
    }

    public void confirmarEdicao() {
        try {
            service.update(user);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Editar um Usuário."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao editar um Usuário.", ex);
        }
        this.editando = false;
        user = new User();
        init();
    }

    public void excluir() {
        try {
            service.delete(user);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Excluir um Usuário."));
            logger.error(ex);
        } catch (ICRMException ex) {
            logger.error("Problema ao excluir um Usuário.", ex);
        }
        user = new User();
        this.editando = false;
        init();
    }

    public void cancelarEdicao() {
        this.user = new User();
        this.editando = false;
    }

    public void cancelarExclusao() {
        this.user = new User();
    }
    //</editor-fold>
}
