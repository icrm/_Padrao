package br.com.idit.exception;

public class InactiveUserException extends SecurityException {

    public InactiveUserException() {
        super("O Usuário informado está Inativo.");
    }
}
