package br.com.icrm.bean.upload;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.exception.PermissionException;
import br.com.icrm.persistence.entity.Produto;
import br.com.icrm.persistence.entity.ProdutoImagem;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Session;
import br.com.icrm.service.ProdutoImagemService;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "imageUpload")
@ViewScoped
public class ImageUpload {

    private static final Logger LOGGER;
    private ProdutoImagem produtoImagem = new ProdutoImagem();
    private Produto produto = new Produto();
    
    static {
        LOGGER = Logger.getLogger(ImageUpload.class);
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public String goToPage() {
        return "/admin/produto/imagem/add_imagem_frame.xhtml";
    }

    public void handleImageUpload(FileUploadEvent event) {
        LOGGER.debug("Recuperando Sessão.");
        final Session session = (Session) FacesContext.getCurrentInstance()
                .getELContext().getELResolver().getValue(FacesContext
                .getCurrentInstance().getELContext(), null, "accmmSession");
        LOGGER.debug("Recuperando o usuário logado.");
        final User logged = session.getLoggedUser();
        LOGGER.debug("Criando objeto ProdutoImagemService.");
        ProdutoImagemService service = new ProdutoImagemService();
        service.setUser(logged);
        LOGGER.debug("Iniciando Upload de Imagens para o Produto [" + produto.getNmProduto() + "].");
        produtoImagem.setProduto(produto);
        produtoImagem.setNmImagem(event.getFile().getFileName());
        produtoImagem.setDsType(event.getFile().getContentType());
        produtoImagem.setDsContent(event.getFile().getContents());
        produtoImagem.setNuSize(event.getFile().getSize());
        try {
            LOGGER.debug("Inserindo Imagem [" + produtoImagem.getNmImagem() + "].");
            service.insert(produtoImagem);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para "
                            + "inserir uma nova Imagem."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao inserir uma nova Imagem", ex);
        }
        produtoImagem = new ProdutoImagem();
    }
}
