package br.com.icrm.bean.menu;

import br.com.icrm.base.exception.ICRMException;
import br.com.icrm.persistence.entity.Module;
import br.com.icrm.persistence.entity.Page;
import br.com.icrm.persistence.entity.User;
import br.com.icrm.security.Session;
import br.com.icrm.service.ModuleService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**
 * Classe resposnável por pela criação do menu do módulo administrativo.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@ManagedBean(name = "adminMenu")
@ViewScoped
public class AdminMenu implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = 6473712398745653123L;
    /**
     * Objeto de Log.
     */
    private static final Logger LOGGER;
    /**
     * Objeto princial para criação do Menu.
     */
    private MenuModel menu;
    /**
     * Lista de Módulos aos quais o Usuário tem permissão de Acessar.
     */
    private List<Module> userModules = new ArrayList<Module>();
    /**
     * Lista de Páginas as quais o Usuário tem permissão de Acessar.
     */
    private List<Page> userPages = new ArrayList<Page>();

    static {
        LOGGER = Logger.getLogger(AdminMenu.class);
    }

    /**
     * Retorna o valor da propriedade menu.
     *
     * @return MenuModel
     * @see MenuModel
     */
    public MenuModel getMenu() {
        return menu;
    }

    /**
     * Define o valor da propriedade menu.
     *
     * @param menu - Objeto Menu
     */
    public void setMenu(final MenuModel menu) {
        this.menu = menu;
    }

    /**
     * Método executado no momento em que a classe é executada.
     */
    @PostConstruct
    private void init() {
        LOGGER.debug("Recuperando Sessão.");
        final Session session = (Session) FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "accmmSession");
        LOGGER.debug("Recuperando o usuário logado.");
        final User logged = session.getLoggedUser();
        LOGGER.debug("Criando objeto ModuleService.");
        final ModuleService service = new ModuleService();
        LOGGER.debug("Usuário Logado: " + logged.getNmUser());
        this.userPages = session.getLoggedUserPages();
        LOGGER.debug("Usuário [" + logged.getNmUser() + "] possui acesso a "
                + userPages.size() + " Páginas.");
        this.userModules = getUserModules(userPages);
        service.setUser(logged);
        try {
            LOGGER.debug("Construindo o Menu.");
            menu = new DefaultMenuModel();
            for (Module module : service.findAllOrphans()) {
                if (!userModules.contains(module)) {
                    LOGGER.debug("Usuário não pode acessar o Módulo ["
                            + module.getNmModule()
                            + "] - Módulo não adicionado ao Menu.");
                    continue;
                }
                Submenu submenu = new Submenu();
                submenu.setLabel(module.getNmModule());
                submenu = createMenu(submenu, module.getChildren());
                this.addPages(submenu, module);
                menu.addSubmenu(submenu);
            }
        } catch (ICRMException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Método resposável pela criação do Menu.
     *
     * @param pai - Menu Superior
     * @param modules - Módulos a serem incorporados no menu
     * @return Submenu
     * @see Submenu
     */
    private Submenu createMenu(final Submenu pai, final List<Module> modules) {
        if (userPages != null) {
            for (Module module : modules) {
                if (!userModules.contains(module)) {
                    LOGGER.debug("Usuário não pode acessar o Módulo ["
                            + module.getNmModule()
                            + "] - Módulo não adicionado ao Menu.");
                    continue;
                }
                Submenu submenu = new Submenu();
                submenu.setLabel(module.getNmModule());
                if (module.getChildren() != null
                        && module.getChildren().size() > 0) {
                    submenu = createMenu(submenu, module.getChildren());
                }
                if (module.getPages() != null && module.getPages().size() > 0) {
                    this.addPages(submenu, module);
                }
                pai.getChildren().add(submenu);
            }
        }
        return pai;
    }

    private void addPages(Submenu submenu, Module module) {
        for (Page page : module.getPages()) {
            if (!userPages.contains(page)) {
                continue;
            }
            if (page.getFgShowMenu() == 0) {
                continue;
            }
            final MenuItem mitem = new MenuItem();
            mitem.setValue(page.getNmPage());
            mitem.setUrl(page.getDsURL());
            submenu.getChildren().add(mitem);
        }
    }

    private List<Module> getUserModules(final List<Page> userPages) {
        final Set<Module> uniqueModules = new HashSet<Module>();
        for (Page page : userPages) {
            uniqueModules.add(page.getModule());
            Module module = page.getModule();
            while (module != null) {
                uniqueModules.add(module.getParent());
                module = module.getParent();
            }
        }
        final List<Module> userModules = new ArrayList<Module>();
        userModules.addAll(uniqueModules);
        return userModules;
    }
}
