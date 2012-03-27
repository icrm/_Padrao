package br.com.idit.bean;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.converter.SecretQuestionConverter;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.entity.SecretQuestion;
import br.com.idit.security.Session;
import br.com.idit.service.SecretQuestionService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ManagedBean(name = "secretQuestionBean")
@ViewScoped
public class SecretQuestionBean implements Serializable {

    private static final long serialVersionUID = 7821384872342934434L;
    private static final Logger LOGGER;
    private SecretQuestionService service = new SecretQuestionService();
    private SecretQuestion question = new SecretQuestion();
    private List<SecretQuestion> questions = new ArrayList<SecretQuestion>();
    private List<SecretQuestion> activeQuestions = new ArrayList<SecretQuestion>();
    private boolean editando = false;

    static {
        LOGGER = Logger.getLogger(SecretQuestionBean.class);
    }

    @PostConstruct
    private void init() {
        Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        if (session.getLoggedUser() != null) {
            service.setUser(session.getLoggedUser());
            try {
                questions = service.findAll();
                activeQuestions = service.findAllActive();
            } catch (PermissionException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para visualizar as informações das Perguntas Secretas."));
                LOGGER.error(ex);
            } catch (ICRMException ex) {
                LOGGER.error("Problema ao executar PostConstruct", ex);
            }
        }
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public SecretQuestion getQuestion() {
        return question;
    }

    public void setQuestion(SecretQuestion question) {
        this.question = question;
    }

    public List<SecretQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SecretQuestion> questions) {
        this.questions = questions;
    }

    public List<SecretQuestion> getActiveQuestions() {
        return activeQuestions;
    }

    public void setActiveQuestions(List<SecretQuestion> activeQuestions) {
        this.activeQuestions = activeQuestions;
    }

    public List<SelectItem> getListItems() {
        final List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (SecretQuestion p : getQuestions()) {
            listItems.add(new SelectItem(p, p.getDsQuestion()));
        }
        return listItems;
    }
    
    public List<SelectItem> getActiveListItems() {
        final List<SelectItem> listItems = new ArrayList<SelectItem>();
        for (SecretQuestion p : getActiveQuestions()) {
            listItems.add(new SelectItem(p, p.getDsQuestion()));
        }
        return listItems;
    }

    public SecretQuestionConverter getConverter() {
        return new SecretQuestionConverter(service);
    }

    public void inserir() {
        try {
            service.insert(question);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Você não tem permissão para "
                            + "Incluir uma Pergunta Secreta."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao inserir uma nova Pergunta Secreta.", ex);
        }
        question = new SecretQuestion();
        init();
    }

    public void editar() {
        this.editando = true;
        this.getQuestions().remove(this.question);
    }

    public void confirmarEdicao() {
        try {
            service.update(question);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Editar uma Pergunta Secreta."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao editar uma Pergunta Secreta.", ex);
        }
        this.editando = false;
        question = new SecretQuestion();
        init();
    }

    public void excluir() {
        try {
            service.delete(question);
        } catch (PermissionException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não tem permissão para Excluir uma Pergunta Secreta."));
            LOGGER.error(ex);
        } catch (ICRMException ex) {
            LOGGER.error("Problema ao excluir uma Pergunta Secreta.", ex);
        }
        question = new SecretQuestion();
        this.editando = false;
        init();
    }

    public void cancelarEdicao() {
        this.question = new SecretQuestion();
        this.editando = false;
        init();
    }

    public void cancelarExclusao() {
        this.question = new SecretQuestion();
    }
}
