package br.com.icrm.converter;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Produto;
import br.com.icrm.service.ProdutoService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.log4j.Logger;

public class ProdutoConverter implements Converter {
    
    private static final Logger LOGGER;
    private ProdutoService service;
    
    static {
        LOGGER = Logger.getLogger(ProdutoConverter.class);
    }
    
    public ProdutoConverter(ProdutoService service) {
        this.service = service;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Produto p = null;
        try {
            p = service.findByName(string);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Você não tem permissão para "
                            + "visualizar as informações de Páginas."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao utilizar Converter", ex);
        }
        return p;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof Produto) {
            return ((Produto) o).getNmProduto();
        } else {
            return null;
        }
    }
}
