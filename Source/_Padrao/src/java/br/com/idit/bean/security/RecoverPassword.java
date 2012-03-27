package br.com.idit.bean.security;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.persistence.entity.SecretQuestionResponse;
import br.com.idit.persistence.entity.User;
import br.com.idit.service.RecoverPasswordService;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ViewScoped
@ManagedBean(name = "recoverPassword")
public class RecoverPassword implements Serializable {

    private static final long serialVersionUID = 73841824738271238L;
    private static final Logger LOGGER;
    private RecoverPasswordService service = new RecoverPasswordService();
    private SecretQuestionResponse secretQuestionResponse = new SecretQuestionResponse();
    private String email;
    private String response;
    private String password;
    private String repassword;
    private boolean emailOk = false;
    private boolean responseOk = false;

    static {
        LOGGER = Logger.getLogger(RecoverPassword.class);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isEmailOk() {
        return emailOk;
    }

    public void setEmailOk(boolean emailOk) {
        this.emailOk = emailOk;
    }

    public SecretQuestionResponse getSecretQuestionResponse() {
        return secretQuestionResponse;
    }

    public void setSecretQuestionResponse(SecretQuestionResponse secretQuestionResponse) {
        this.secretQuestionResponse = secretQuestionResponse;
    }

    public boolean isResponseOk() {
        return responseOk;
    }

    public void setResponseOk(boolean responseOk) {
        this.responseOk = responseOk;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public void verifyEmail() {
        final User user = service.findByEmail(email);
        if (user != null) {
            this.secretQuestionResponse = (user.getResponses().size() > 0) ? user.getResponses().get(0) : null;
            this.emailOk = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "O E-mail informado não está cadastrado"
                            + " em nossa base de dados.", "O E-mail informado não está cadastrado"
                            + " em nossa base de dados."));
            this.emailOk = false;
        }
    }

    public void verifyResponse() {
        if (this.response.equals(this.secretQuestionResponse.getDsResponse())) {
            this.responseOk = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Resposta para a pergunta "
                            + "está incorreta.", "A Resposta para a pergunta "
                            + "está incorreta."));
            this.responseOk = false;
        }
    }

    public String changePassword() {
        if (!(this.password.equals(repassword))) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "As Senhas digitadas não são iguais.", "As Senhas digitadas não são iguais."));
            return null;
        }

        try {
            final User user = service.findByEmail(email);
            user.setDsPassword(password);
            service.changePassword(user);
            this.emailOk = false;
            this.responseOk = false;
            this.password = null;
            this.repassword = null;
            this.response = null;
            this.secretQuestionResponse = new SecretQuestionResponse();
            this.email = null;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "A senha foi alterada com sucesso!", "A senha foi alterada com sucesso!"));
        } catch (ICRMException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(ex.getMessage()));
        }

        return "login.xhtml";
    }
}
