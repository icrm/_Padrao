package br.com.icrm.base.exception;

/**
 * Classe para tratar exceções gerais da aplicação.
 *
 * @since 0.1
 * @version 0.1
 * @see Exception
 */
public class ICRMException extends Exception {

    /**
     * Construtor que recebe Throwable.
     *
     * @param cause - Causa da Exceção
     */
    public ICRMException(final Throwable cause) {
        super(cause);
    }

    /**
     * Construtor que recebe a Mensagem e Throwable.
     *
     * @param message - Mensagem de erro
     * @param cause - Causada Exceção
     */
    public ICRMException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor que recebe a Mensagem.
     *
     * @param message - Mensagem de erro
     */
    public ICRMException(final String message) {
        super(message);
    }

    /**
     * Construtor Padrão.
     */
    public ICRMException() {
        /**
         * COnstrutor padrão.
         */
    }

}
