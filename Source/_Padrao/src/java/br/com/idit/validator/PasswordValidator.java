package br.com.idit.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.log4j.Logger;

@FacesValidator("password")
public class PasswordValidator implements Validator {
    
    private static final Logger LOGGER;
    
    static {
        LOGGER = Logger.getLogger(PasswordValidator.class);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        LOGGER.debug("Limpando valor.");
        final String clean = ((String) value).trim();
        LOGGER.debug("Valor Limpo : [" + clean + "]");
        boolean isValid = true;

        if (isValid && "".equals(clean)) {
            isValid = false;
            LOGGER.debug("Nenhum valor foi informado.");
            final FacesMessage msg =
                    new FacesMessage("A Senha não pode ser formada só"
                            + " por espaços.",
                            "A Senha não pode ser formada só por espaços.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            LOGGER.debug("Finalizando validação de Password.");
            throw new ValidatorException(msg);
        }

        if (isValid && clean.length()<6) {
            LOGGER.debug("A Senha deve conter no mínimo 6 caracteres.");
            final FacesMessage msg =
                    new FacesMessage("A Senha deve conter no mínimo 6 "
                            + "caracteres.",
                            "A Senha deve conter no mínimo 6 caracteres.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            LOGGER.debug("Finalizando validação de Password.");
            throw new ValidatorException(msg);
        }
    }
}
