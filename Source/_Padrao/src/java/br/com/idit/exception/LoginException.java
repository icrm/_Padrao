package br.com.idit.exception;

public class LoginException extends SecurityException {

    public LoginException() {
        super("Usuário e/ou Senha inválidos!");
    }
}
