    package br.com.icrm.filter;

import br.com.icrm.persistence.entity.Page;
import br.com.icrm.security.Session;
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

public class AdminFilter implements Filter {

    private FilterConfig filterConfig = null;
    private final String SIGNON_PAGE_URI = "/admin/login.xhtml";
    private final String ACCESS_DENIED_PAGE_URI = "/access_denied.xhtml";
    private final String INDEX_PAGE = "/admin/index.xhtml";
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
        HttpServletRequest req = (HttpServletRequest) request;
        LOGGER.debug("Definindo Objeto HttpServletResponse.");
        HttpServletResponse resp = (HttpServletResponse) response;

        String url = req.getRequestURI().replace(req.getContextPath(), "");

        LOGGER.debug("Recuperando a Sessão.");
        Session session = (Session) req.getSession().getAttribute("accmmSession");

        if (session == null) {
            LOGGER.debug("Sessão está nula");
            req.getSession().setAttribute("redirectTo", url);
            request.getRequestDispatcher(SIGNON_PAGE_URI).forward(req, resp);
            return;
        } else if (session.getLoggedUser() == null) {
            LOGGER.debug("A Sessão não está nula, mas o Usuário está.");
            req.getSession().setAttribute("redirectTo", url);
            request.getRequestDispatcher(SIGNON_PAGE_URI).forward(req, resp);
            return;
        } else if (session.getLoggedUser() != null && !checkPagePermission(session, url)) {
            LOGGER.debug("A Sessão e o Usuário não estão nulos, mas o usuário não tem permissão para acessar a página informada");
            request.getRequestDispatcher(ACCESS_DENIED_PAGE_URI).forward(req, resp);
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

    private boolean checkPagePermission(Session session, String url) {
        if (url.equals(SIGNON_PAGE_URI)) {
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
}
