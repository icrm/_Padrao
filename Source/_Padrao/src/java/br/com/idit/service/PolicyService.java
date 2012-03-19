package br.com.idit.service;

import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
import br.com.idit.persistence.dao.PolicyDAO;
import br.com.idit.persistence.entity.Policy;
import br.com.idit.persistence.entity.User;
import br.com.idit.security.Security;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Classe com métodos de Serviço para a Entidade UserStatus.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 */
@SessionScoped
@ManagedBean(name = "policyService")
public class PolicyService implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = -1L;
    /**
     * Variável que representa o DAO da entidade Policy.
     */
    private final PolicyDAO policyDAO = new PolicyDAO();
    /**
     * Variável que representa o Objeto User.
     */
    private User user;
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para recuperar Objetos Policy.
     */
    private static final String GET_PERMISSION = "getPolicy";
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para excluir Objetos Policy.
     */
    private static final String DELETE_PERMISSION = "deletePolicy";
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para listar Objetos Policy.
     */
    private static final String LIST_PERMISSION = "listPolicy";
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para inserir Objetos Policy.
     */
    private static final String INSERT_PERMISSION = "insertPolicy";
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para atualizar Objetos Policy.
     */
    private static final String UPDATE_PERMISSION = "editPolicy";

    /**
     * Define o valor da propriedade user.
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Retorna o valor da propriedade user.
     *
     * @return User - Usuário
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Recupera um Objeto Policy a partir de seu nome.
     *
     * @param name
     * @return Policy
     * @throws ICRMException
     * @see ICRMException
     */
    public Policy findByName(final String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, GET_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), GET_PERMISSION);
        }
        return policyDAO.findByName(name);
    }

    /**
     * Método para deleção de Objetos.
     *
     * @param policy - Objeto a ser excluído.
     */
    public void delete(final Policy policy) throws ICRMException {
        if (!Security.checkPolicy(this.user, DELETE_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), DELETE_PERMISSION);
        }
        policyDAO.delete(policy);
    }

    /**
     * Método que retorna uma lista com todos os Objetos persistidos.
     *
     * @return List<Policy> - Lista com todos os Objetos
     */
    public List<Policy> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, LIST_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), LIST_PERMISSION);
        }
        return policyDAO.findAll();
    }

    /**
     * Método que retorna um Objeto Policy.
     *
     * @param identifier - identificador do Objeto.
     * @return Policy - Objeto.
     */
    public Policy findById(final Object identifier) throws ICRMException {
        if (!Security.checkPolicy(this.user, GET_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), GET_PERMISSION);
        }
        return policyDAO.findById(identifier);
    }

    /**
     * Método para inserção de Objetos.
     *
     * @param policy - Objeto a ser inserido.
     * @return Policy
     */
    public Policy insert(final Policy policy) throws ICRMException {
        if (!Security.checkPolicy(this.user, INSERT_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), INSERT_PERMISSION);
        }
        return policyDAO.insert(policy);
    }

    /**
     * Método para atualização de Objetos.
     *
     * @param policy - Objeto a ser atualizado.
     * @return Policy
     */
    public Policy update(final Policy policy) throws ICRMException {
        if (!Security.checkPolicy(this.user, UPDATE_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), UPDATE_PERMISSION);
        }
        return policyDAO.update(policy);
    }
}
