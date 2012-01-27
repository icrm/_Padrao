package br.com.icrm.exception;

public class InactiveUserException extends SecurityException {

    public InactiveUserException() {
        super("O Usuário informado está Inativo.");
    }
}
