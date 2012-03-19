package br.com.idit.exception.handler.factory;

import br.com.idit.exception.handler.ViewExpiredExceptionHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ViewExpiredExceptionHandlerFactory
        extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    public ViewExpiredExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new ViewExpiredExceptionHandler(result);
        return result;
    }
}
