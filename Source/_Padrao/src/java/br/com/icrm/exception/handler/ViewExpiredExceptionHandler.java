package br.com.icrm.exception.handler;

import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.apache.log4j.Logger;

public class ViewExpiredExceptionHandler extends ExceptionHandlerWrapper {

    private static final Logger LOGGER;
    private ExceptionHandler wrapped;
    
    static {
        LOGGER = Logger.getLogger(ViewExpiredExceptionHandler.class);
    }

    public ViewExpiredExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();

            if (t instanceof ViewExpiredException) {
                ViewExpiredException vee = (ViewExpiredException) t;
                FacesContext fc = FacesContext.getCurrentInstance();
                String requestType = fc.getExternalContext().getRequestHeaderMap().get("Faces-Request");
                
                if ("partial/ajax".equals(requestType)) {
                    throw new FacesException();
                }
                
                Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
                NavigationHandler nav = fc.getApplication().getNavigationHandler();
                try {
                    FacesMessage msg = new FacesMessage("Sessão Expirada!");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, msg);
                    requestMap.put("currentViewId", vee.getViewId());
                    nav.handleNavigation(fc, null, fc.getExternalContext().getInitParameter("iit.VIEW_EXPIRED_PAGE"));
                    fc.renderResponse();
                } finally {
                    i.remove();
                }
            }
        }

        getWrapped().handle();
    }
}
