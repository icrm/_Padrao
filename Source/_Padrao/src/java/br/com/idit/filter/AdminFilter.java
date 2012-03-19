package br.com.idit.filter;

import br.com.idit.persistence.entity.Page;
import br.com.idit.security.Session;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Filtro para a área administrativa.
 *
 * @since 0.1
 * @version 0.1
 * @see Filter
 */
public class AdminFilter implements Filter {

    /**
     * Configurações do Filtro.
     */
    private FilterConfig filterConfig = null;
    /**
     * Página de Login.
     */
    private final String SIGNON_PAGE = "/admin/login.xhtml";
    /**
     * Página de acesso negado.
     */
    private final String ACCESS_DENIED_PAGE = "/access_denied.xhtml";
    /**
     * Página inicial da aplicação.
     */
    private final String INDEX_PAGE = "/admin/index.xhtml";
    /**
     * Objeto de Log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(AdminFilter.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        LOGGER.debug("Iniciando execução do Filtro do módulo Administrativo.");

        LOGGER.debug("Definindo Objeto HttpServletRequest.");
        final HttpServletRequest req = (HttpServletRequest) request;
        LOGGER.debug("Definindo Objeto HttpServletResponse.");
        final HttpServletResponse resp = (HttpServletResponse) response;

        final String url = cleanUrl(req.getRequestURI(), req.getContextPath());

        LOGGER.debug("Recuperando a Sessão.");
        Session session 
                = (Session) req.getSession().getAttribute("accmmSession");

        if (session == null) {
            LOGGER.debug("Sessão está nula");
            request.setAttribute("redirectTo", url);
            request.getRequestDispatcher(SIGNON_PAGE).forward(req, resp);
            return;
        } else if (session.getLoggedUser() == null) {
            LOGGER.debug("A Sessão não está nula, mas o Usuário está.");
            request.setAttribute("redirectTo", url);
            request.getRequestDispatcher(SIGNON_PAGE).forward(req, resp);
            return;
        } else if (session.getLoggedUser() != null
                && !checkPagePermission(session, url)) {
            LOGGER.debug("A Sessão e o Usuário não estão nulos, mas o "
                    + "usuário não tem permissão para acessar a "
                    + "página [" + url + "]");
            request.getRequestDispatcher(ACCESS_DENIED_PAGE).forward(req, resp);
            return;
        }

        LOGGER.debug("Dando continuidade a execução.");
        chain.doFilter(req, resp);
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AdminFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    private boolean checkPagePermission(final Session session
            , final String url) {
        if (url.equals(SIGNON_PAGE) || url.equals(INDEX_PAGE)) {
            return true;
        }
        boolean permission = false;
        if (session == null || url.equals("")) {
            return false;
        }
        if (session.getLoggedUserPages() == null) {
            return false;
        }
        for (Page p : session.getLoggedUserPages()) {
            if (url.equals(p.getDsURL())) {
                permission = true;
                break;
            }
        }
        return permission;
    }

    private String cleanUrl(final String url, final String contextPath) {
        final int index = (url.indexOf(";") > -1) ?
                url.indexOf(";") : url.length();
        final String clean = url.substring(0, index).replace(contextPath, "");
        return clean;
    }
}
