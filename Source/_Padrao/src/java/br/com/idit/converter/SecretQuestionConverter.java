package br.com.idit.converter;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.SecretQuestion;
import br.com.idit.service.SecretQuestionService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class SecretQuestionConverter implements Converter {

    private static final Logger LOGGER;
    private SecretQuestionService service;

    static {
        LOGGER = Logger.getLogger(SecretQuestionConverter.class);
    }

    public SecretQuestionConverter(SecretQuestionService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        SecretQuestion passphrase = null;
        try {
            passphrase = service.findByDescription(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações das Perguntas Secretas."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao utilizar Converter", ex);
        }
        return passphrase;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof SecretQuestion) {
            return ((SecretQuestion) o).getDsQuestion();
        } else {
            return null;
        }
    }

}
