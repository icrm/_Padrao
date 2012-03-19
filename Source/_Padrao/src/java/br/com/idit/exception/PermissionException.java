package br.com.idit.exception;

/**
 * Classe criada para tratar exceções de Permissão.
 *
 * @since 0.1
 * @version 0.1
 * @see SecurityException
 */
public class PermissionException extends SecurityException {

    /**
     * Nome de usuário.
     */
    private final String userName;
    /**
     * Função da diretiva.
     */
    private final String policyFunction;

    /**
     * Construtor que recebe o nome do usuário e função para a qual foi lançada
     * a Exceção.
     *
     * @param userName - Nome do usuário
     * @param policyFunction - Função da Diretiva
     */
    public PermissionException(final String userName
            , final String policyFunction) {
        this.userName = userName;
        this.policyFunction = policyFunction;
    }

    @Override
    public String getMessage() {
        final StringBuilder sbuilder = new StringBuilder();
        sbuilder.append("O Usuário ");
        sbuilder.append(userName);
        sbuilder.append(
                " não possui permissão para executar a tarefa solicitada! (");
        sbuilder.append(policyFunction);
        sbuilder.append(")");
        return sbuilder.toString();
    }
}
