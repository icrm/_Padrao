package br.com.icrm.bean;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.converter.ModuleStatusConverter;
import br.com.icrm.converter.ProdutoStatusConverter;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.ProdutoStatus;
import br.com.icrm.security.Session;
import br.com.icrm.service.ProdutoStatusService;
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
@ManagedBean(name = "produtoStatusBean")
public class ProdutoStatusBean implements Serializable {

    private static final long serialVersionUID = 6253162383987123L;
    private static final Logger LOGGER;
    private ProdutoStatusService service = new ProdutoStatusService();
    private ProdutoStatus produtoStatus = new ProdutoStatus();
    private List<ProdutoStatus> produtosStatus = new ArrayList<ProdutoStatus>();
    
    static {
        LOGGER = Logger.getLogger(ProdutoStatusBean.class);
    }

    public ProdutoStatus getProdutoStatus() {
        return produtoStatus;
    }

    public void setProdutoStatus(ProdutoStatus produtoStatus) {
        this.produtoStatus = produtoStatus;
    }

    public List<ProdutoStatus> getProdutosStatus() {
        return produtosStatus;
    }

    public void setProdutosStatus(List<ProdutoStatus> produtosStatus) {
        this.produtosStatus = produtosStatus;
    }
    
    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance()
                .getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                this.produtosStatus = service.findAll();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Você não tem permissão "
                                + "para visualizar as informações "
                                + "de Status dos Produtos."));
                LOGGER.error(ex);
            } catch (ICRMException ex) {
                LOGGER.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    public List<SelectItem> getListItems() {
        List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (ProdutoStatus ms : getProdutosStatus()) {
            listItems.add(new SelectItem(ms, ms.getNmStatus()));
        }
        return listItems;
    }

    public ProdutoStatusConverter getConverter() {
        return new ProdutoStatusConverter(service);
    }
}
