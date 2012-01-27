package br.com.icrm.exception;

public class LoginException extends SecurityException {

    public LoginException() {
        super("Usuário e/ou Senha inválidos!");
    }
}
