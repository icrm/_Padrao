package br.com.idit.exception;

import br.com.idit.base.exception.ICRMException;

/**
 * Classe cirada para tratar exceções Gerais da Aplicação.
 *
 * @since 0.1
 * @version 0.1
 * @see ICRMException
 */
public class SecurityException extends ICRMException {

    /**
     * Construtora padrão.
     */
    public SecurityException() {
        /**
         * Construtora padrão.
         */
    }

    /**
     * Construtor que recebe a Mensagem.
     *
     * @param message - Mensagem de erro
     */
    public SecurityException(final String message) {
        super(message);
    }

    /**
     * Construtor que recebe a Mensagem e Throwable.
     *
     * @param message - Mensagem de erro
     * @param cause - Causada Exceção
     */
    public SecurityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor que recebe Throwable.
     *
     * @param cause - Causa da Exceção
     */
    public SecurityException(final Throwable cause) {
        super(cause);
    }
}
