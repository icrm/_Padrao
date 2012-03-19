package br.com.idit.bean;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.converter.UserStatusConverter;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.UserStatus;
import br.com.idit.security.Session;
import br.com.idit.service.UserStatusService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@RequestScoped
@ManagedBean(name = "userStatusBean")
public class UserStatusBean implements Serializable {

    //<editor-fold desc="ATRIBUTES">
    private static final long serialVersionUID = -1L;
    private static final Logger logger;
    private UserStatusService service = new UserStatusService();
    private UserStatus userStatus = new UserStatus();
    private List<UserStatus> usersStatus = new ArrayList<UserStatus>();
    //</editor-fold>

    static {
        logger = Logger.getLogger(UserStatusBean.class);
    }

    public UserStatusBean() {
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                this.usersStatus = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações de Status do Usuário."));
            } catch (ICRMException ex) {
                logger.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    //<editor-fold desc="GETTERS AND SETTERS">
    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public List<UserStatus> getUsersStatus() {
        return usersStatus;
    }

    public void setUsersStatus(List<UserStatus> usersStatus) {
        this.usersStatus = usersStatus;
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (UserStatus us : getUsersStatus()) {
            listItems.add(new SelectItem(us, us.getNmStatus()));
        }
        return listItems;
    }
    //</editor-fold>

    public UserStatusConverter getConverter() {
        return new UserStatusConverter(service);
    }
}
