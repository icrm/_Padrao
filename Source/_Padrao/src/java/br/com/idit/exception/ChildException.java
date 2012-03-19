package br.com.idit.exception;

import br.com.idit.base.exception.ICRMException;

/**
 * Classe criada para tratar exceções na Remocao de Objetos Pais.
 *
 * @since 0.1
 * @version 0.1
 * @see ICRMException
 */
public class ChildException extends ICRMException {

    /**
     * Nome da Classe do Objeto a ser excluido.
     */
    private final String className;
    /**
     * Nome do Objeto a ser excluido.
     */
    private final String objectName;

    /**
     * Construtor que recebe o nome da classe e nome do Objeto a ser excluido.
     *
     * @param className Nome da Classe do Objeto a ser excluido.
     * @param objetcName Nome do Objeto a ser excluido.
     */
    public ChildException(final String className, final String objectName) {
        this.className = className;
        this.objectName = objectName;
    }

    @Override
    public String getMessage() {
        final StringBuilder sbuilder = new StringBuilder();
        sbuilder.append("O(A) ");
        sbuilder.append(className);
        sbuilder.append(" ");
        sbuilder.append(objectName);
        sbuilder.append(" só poderá ser excluido depois que os objetos");
        sbuilder.append(" associados a ele forem excluídos.");
        return sbuilder.toString();
    }
}
