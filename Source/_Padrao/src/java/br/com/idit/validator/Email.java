package br.com.idit.validator;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.persistence.entity.User;
import br.com.idit.security.Session;
import br.com.idit.service.UserService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.log4j.Logger;

@FacesValidator("br.com.icrm.validator.Email")
public class Email implements Validator {

    private static final Logger LOGGER;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
            + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
            + "(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    static {
        LOGGER = Logger.getLogger(Email.class);
    }

    public Email() {
        LOGGER.debug("Iniciando validação de E-mail.");
        LOGGER.debug("Definindo Pattern para validação.");
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(final FacesContext context, final UIComponent component
            , final Object value) {
        LOGGER.debug("Verificando se o E-mail é compatível com o Pattern.");
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            LOGGER.debug("Email inválido.");
            final FacesMessage msg =
                    new FacesMessage("O E-mail informado parece inválido.",
                            "O E-mail informado parece inválido.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            LOGGER.debug("Finalizando validação de E-mail");
            throw new ValidatorException(msg);
        }

        LOGGER.debug("Recuperando Objeto da Sessão.");
        final Session session = (Session) FacesContext.getCurrentInstance()
                .getELContext().getELResolver().getValue(FacesContext
                .getCurrentInstance().getELContext(), null, "accmmSession");
        LOGGER.debug("Criando instância de UserService.");
        final UserService service = new UserService();
        LOGGER.debug("Definindo Usuário para execução da Classe Service.");
        service.setUser(session.getLoggedUser());
        User user = null;
        try {
             user = service.findByEmail(value.toString());
        } catch (ICRMException ex) {
            LOGGER.debug(ex);
        }
        if (user != null) {
            final FacesMessage msg =
                    new FacesMessage("O E-mail informado já está cadastrado.",
                            "O E-mail informado já está cadastrado.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
