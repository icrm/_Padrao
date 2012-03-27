package br.com.idit.service;

import br.com.idit.persistence.dao.UserStatusDAO;
import br.com.idit.persistence.entity.User;
import br.com.idit.persistence.entity.UserStatus;
import br.com.idit.base.exception.ICRMException;
import br.com.idit.exception.PermissionException;
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
@ManagedBean(name = "userStatusService")
public class UserStatusService implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = -1L;
    /**
     * Variável que representa o DAO da entidade UserStatus.
     */
    private final UserStatusDAO userStatusDAO = new UserStatusDAO();
    /**
     * Variável que representa o Objeto User.
     */
    private User user;
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para recuperar Objetos UserStatus.
     */
    private static final String GET_PERMISSION = "getUserStatus";
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para excluir Objetos UserStatus.
     */
    private static final String DELETE_PERMISSION = "deleteUserStatus";
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para listar Objetos UserStatus.
     */
    private static final String LIST_PERMISSION = "listUserStatus";
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para inserir Objetos UserStatus.
     */
    private static final String INSERT_PERMISSION = "insertUserStatus";
    /**
     * Constante com o valor da função a ser verificado para dar permissão
     * para atualizar Objetos UserStatus.
     */
    private static final String UPDATE_PERMISSION = "editUserStatus";

    /**
     * Define o valor da propriedade user.
     *
     * @param user
     */
    public void setUser(final User user) {
        this.user = user;
    }

    /**
     * Retorna o valor da propriedade user.
     *
     * @return User - Usuário
     */
    public User getUser() {
        return user;
    }

    /**
     * Recupera um Objeto UserStatus a partir de seu nome.
     *
     * @param name
     * @return UserStatus
     * @throws ICRMException
     * @see ICRMException
     */
    public UserStatus findByName(final String name) throws ICRMException {
        if (!Security.checkPolicy(this.user, GET_PERMISSION, "insertUser")) {
            throw new PermissionException(user.getNmUser(), GET_PERMISSION);
        }
        return userStatusDAO.findByName(name);
    }

    /**
     * Método para deleção de Objetos.
     *
     * @param userStatus - Objeto a ser excluído.
     */
    public void delete(final UserStatus userStatus) throws ICRMException {
        if (!Security.checkPolicy(this.user, DELETE_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), DELETE_PERMISSION);
        }
        userStatusDAO.delete(userStatus);
    }

    /**
     * Método que retorna uma lista com todos os Objetos persistidos.
     *
     * @return List<UserStatus> - Lista com todos os Objetos
     */
    public List<UserStatus> findAll() throws ICRMException {
        if (!Security.checkPolicy(this.user, LIST_PERMISSION, "insertUser")) {
            throw new PermissionException(user.getNmUser(), LIST_PERMISSION);
        }
        return userStatusDAO.findAll();
    }

    /**
     * Método que retorna um Objeto UserStatus.
     *
     * @param identifier - identificador do Objeto.
     * @return UserStatus - Objeto.
     */
    public UserStatus findById(final Object identifier) throws ICRMException {
        if (!Security.checkPolicy(this.user, GET_PERMISSION, "insertUser")) {
            throw new PermissionException(user.getNmUser(), GET_PERMISSION);
        }
        return userStatusDAO.findById(identifier);
    }

    /**
     * Método para inserção de Objetos.
     *
     * @param userStatus - Objeto a ser inserido.
     * @return UserStatus
     */
    public UserStatus insert(final UserStatus userStatus) throws ICRMException {
        if (!Security.checkPolicy(this.user, INSERT_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), INSERT_PERMISSION);
        }
        return userStatusDAO.insert(userStatus);
    }

    /**
     * Método para atualização de Objetos.
     *
     * @param userStatus - Objeto a ser atualizado.
     * @return UserStatus
     */
    public UserStatus update(final UserStatus userStatus) throws ICRMException {
        if (!Security.checkPolicy(this.user, UPDATE_PERMISSION)) {
            throw new PermissionException(user.getNmUser(), UPDATE_PERMISSION);
        }
        return userStatusDAO.update(userStatus);
    }
}
