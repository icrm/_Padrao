package br.com.icrm.base.exception;

import java.sql.SQLException;

/**
 * Classe para tratar exceções de Banco de Dados da Aplicação.
 *
 * @since 0.1
 * @version 0.1
 * @see RuntimeException
 */
public class DataBaseException extends RuntimeException {

    /**
     * Código de erro retornado pelo Banco de Dados.
     */
    private int errorCode;

    /**
     * Retorna o Código de Erro da Exceção.
     *
     * @return int - Código do Erro
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Define o Código de Erro da Exceção.
     *
     * @param errorCode
     */
    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Construtor que recebe Throwable.
     *
     * @param cause - Causa da Exceção
     */
    public DataBaseException(final Throwable cause) {
        super(cause);
        final SQLException sqlex = (SQLException) cause;
        this.errorCode = sqlex.getErrorCode();
    }

    /**
     * Construtor que recebe a Mensagem e Throwable.
     *
     * @param message - Mensagem de erro
     * @param cause - Causada Exceção
     */
    public DataBaseException(final String message, final Throwable cause) {
        super(message, cause);
        final SQLException sqlex = (SQLException) cause;
        this.errorCode = sqlex.getErrorCode();
    }
}
