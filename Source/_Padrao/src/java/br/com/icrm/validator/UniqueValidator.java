package br.com.icrm.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.log4j.Logger;

@FacesValidator("unique")
public class UniqueValidator implements Validator {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(UniqueValidator.class);
    }

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        
    }
    
}
