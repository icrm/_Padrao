package br.com.icrm.security;

import br.com.icrm.persistence.entity.Page;
import br.com.icrm.persistence.entity.Policy;
import br.com.icrm.persistence.entity.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "accmmSession")
public class Session implements Serializable {

    private static final long serialVersionUID = -1L;
    private User loggedUser;
    private List<Page> loggedUserPages;
    private List<Policy> loggedUserPolicies;

    public Session() {
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public List<Page> getLoggedUserPages() {
        return loggedUserPages;
    }

    public void setLoggedUserPages(List<Page> loggedUserPages) {
        this.loggedUserPages = loggedUserPages;
    }

    public List<Policy> getLoggedUserPolicies() {
        return loggedUserPolicies;
    }

    public void setLoggedUserPolicies(List<Policy> loggedUserPolicies) {
        this.loggedUserPolicies = loggedUserPolicies;
    }

    public void clear() {
        this.loggedUser = null;
        this.loggedUserPages = null;
        this.loggedUserPolicies = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Session other = (Session) obj;
        if (this.loggedUser != other.loggedUser && (this.loggedUser == null || !this.loggedUser.equals(other.loggedUser))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.loggedUser != null ? this.loggedUser.hashCode() : 0);
        return hash;
    }
}
