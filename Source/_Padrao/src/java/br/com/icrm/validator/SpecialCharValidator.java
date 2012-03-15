package br.com.icrm.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.log4j.Logger;

@FacesValidator("specialChar")
public class SpecialCharValidator implements Validator {

    private static final Logger LOGGER;
    private static final String SPECIAL_PATTERN = "!@#$%*()_{}[]<>:,.;?/\"'\\|-_=+";

    static {
        LOGGER = Logger.getLogger(SpecialCharValidator.class);
    }

    public SpecialCharValidator() {
        LOGGER.debug("Iniciando validação de Caracteres Especiais.");
        LOGGER.debug("Definindo Pattern para validação.");
    }

    @Override
    public void validate(FacesContext context, UIComponent component, final Object value) throws ValidatorException {
        LOGGER.debug("Verificando se o valor é compatível com o Pattern.");
        String clean = ((String) value).trim();

        LOGGER.debug("Valor Limpo : [" + clean + "]");

        if ("".equals(clean)) {
            LOGGER.debug("Nenhum valor foi informado.");
            final FacesMessage msg =
                    new FacesMessage("O valor não pode ser formado só"
                            + " por espaços.",
                            "O valor não pode ser formado só por espaços.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            LOGGER.debug("Finalizando validação de Caracteres Especiais.");
            throw new ValidatorException(msg);
        }

        for (int i = 0; i < SPECIAL_PATTERN.length(); i++) {
            if (clean.indexOf(SPECIAL_PATTERN.charAt(i)) > -1) {
                LOGGER.debug("O Valor informado não pode conter "
                        + "caracteres especiais.");
                final FacesMessage msg =
                        new FacesMessage("O Valor informado não pode conter "
                                + "caracteres especiais.",
                        "O Valor informado não pode conter "
                                + "caracteres especiais.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                LOGGER.debug("Finalizando validação de Caracteres Especiais.");
                throw new ValidatorException(msg);
            }
        }
    }
}