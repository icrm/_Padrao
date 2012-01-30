package br.com.icrm.bean.security;

import br.com.icrm.exception.InactiveUserException;
import br.com.icrm.exception.LoginException;
import br.com.icrm.security.Session;
import br.com.icrm.service.LoginManager;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@RequestScoped
@ManagedBean(name = "login")
public class Login {

    private static final Logger LOGGER;
    private LoginManager lm = new LoginManager();
    private String email;
    private String password;
    private String redirectTo;

    static {
        LOGGER = Logger.getLogger(Login.class);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = cleanUrl(redirectTo);
    }

    public String login() {
        try {
            final Session session = lm.login(email, password);
            if (session.getLoggedUser() != null) {
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("accmmSession", session);
                final Map<String, Object> sessionMap = FacesContext
                        .getCurrentInstance()
                        .getExternalContext().getSessionMap();
                LOGGER.debug("O Usuário será direcionado para a Página ["
                        + redirectTo + "].");
                if ("/admin/login.xhtml".equals(redirectTo)) {
                    redirectTo = "/admin/index.xhtml";
                }
                return redirectTo;
            }
        } catch (LoginException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(ex.getMessage()));
        } catch (InactiveUserException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(ex.getMessage()));
        }
        return "";
    }

    public String logout() {
        lm.logout();
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().clear();
        final HttpSession session = (HttpSession) FacesContext
                .getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "/admin/login.xhtml";
    }

    private String cleanUrl(final String url) {
        final int index = (url.indexOf(";") > -1) ?
                url.indexOf(";") : url.length();
        final String clean = url.substring(0, index);
        return clean;
    }
}
