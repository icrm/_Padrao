package br.com.idit.persistence.dao;

import br.com.idit.base.exception.DataBaseException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 * Classe abstrata com métodos básicos de Persistência, esta classe deve
 * ser utilizada por todas as classes DAO.
 *
 * @since 0.1
 * @version 0.1
 * @see Serializable
 * @param <T> tipo do Objeto que será implementado pela classe
 */
public abstract class AbstractDAO<T> implements Serializable {

    /**
     * Variável que sobrescreve o ID Serial da classe Serializable.
     */
    private static final long serialVersionUID = -1L;
    /**
     * Objeto de log.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(AbstractDAO.class);
    }

    /**
     * Retorna um Objeto EntityManager que gerencia a conexão com o
     * Banco de Dados.
     *
     * @return EntityManager
     * @see EntityManager
     */
    protected EntityManager getEntityManager() {
        LOGGER.debug("Recuperando EntityManager.");
        final EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("PUPadrao");
        return emf.createEntityManager();
    }

    /**
     * Retorna a Classe de Dominio que será utilizada para "tipar"
     * o DAO.
     *
     * @return Class<T>
     */
    protected abstract Class<T> getDomainClass();

    /**
     * Método para inserir um Objeto.
     *
     * @param tipo Objeto a ser inserido.
     * @return <T>
     */
    public T insert(final T tipo) {
        LOGGER.debug("Iniciando [INSERT] da Entidade "
                + getDomainClass().getName() + ".");
        final EntityManager entity = this.getEntityManager();

        try {
            LOGGER.debug("Persistindo a Entidade "
                    + getDomainClass().getName() + ".");
            entity.getTransaction().begin();
            entity.persist(tipo);
            entity.getTransaction().commit();
        } catch (RollbackException ex) {
            LOGGER.debug(ex);
            throw new DataBaseException((SQLException) ex.getCause());
        } catch (Exception ex) {
            LOGGER.error(ex);
        }

        LOGGER.info("Entidade " + getDomainClass().getName()
                + " Persistida com sucesso.");
        LOGGER.debug("Finalizando [INSERT] da Entidade "
                + getDomainClass().getName() + ".");
        return tipo;
    }

    /**
     * Método para atualizar um Objeto.
     *
     * @param tipo Objeto a ser atualizado.
     * @return <T>
     */
    public T update(final T tipo) {
        LOGGER.debug("Iniciando [UPDATE] da Entidade "
                + getDomainClass().getName() + ".");
        final EntityManager entity = this.getEntityManager();

        LOGGER.debug("Atualizando a Entidade "
                + getDomainClass().getName() + ".");
        entity.getTransaction().begin();
        final T objeto = entity.merge(tipo);
        entity.getTransaction().commit();

        LOGGER.info("Entidade " + getDomainClass().getName()
                + " Atualizada com sucesso.");
        LOGGER.debug("Finalizando [UPDATE] da Entidade "
                + getDomainClass().getName() + ".");
        return objeto;
    }

    /**
     * Método para excluir uma Objeto.
     *
     * @param tipo Objeto a ser excluído.
     */
    public void delete(final T tipo) {
        LOGGER.debug("Iniciando [DELETE] da Entidade "
                + getDomainClass().getName() + ".");
        final EntityManager entity = this.getEntityManager();

        LOGGER.debug("Excluindo a Entidade "
                + getDomainClass().getName() + ".");
        entity.getTransaction().begin();
        entity.remove(entity.merge(tipo));
        entity.getTransaction().commit();

        LOGGER.info("Entidade " + getDomainClass().getName()
                + " Excluida com sucesso.");
        LOGGER.debug("Finalizando [DELETE] da Entidade "
                + getDomainClass().getName() + ".");
    }

    /**
     * Método que retorna a quantidade total de Objetos persistidos.
     *
     * @return int Total de Objetos persistidos.
     */
    public long count() {
        LOGGER.debug("Iniciando [COUNT] da Entidade "
                + getDomainClass().getName() + ".");
        final EntityManager entity = this.getEntityManager();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = entity.getCriteriaBuilder().createQuery();

        LOGGER.debug("Criando Instância de Root.");
        final Root<T> root = criteria.from(this.getDomainClass());

        LOGGER.debug("Definindo Query de Contagem.");
        criteria.select(entity.getCriteriaBuilder().count(root));

        LOGGER.info("Contando os registros da Entidade "
                + getDomainClass().getName() + ".");
        final Query query = entity.createQuery(criteria);

        LOGGER.debug("Finalizando [COUNT] da Entidade "
                + getDomainClass().getName() + ".");
        return ((Long) query.getSingleResult()).longValue();
    }

    /**
     * Método que retorna todos Objetos já persistidos.
     *
     * @return List<T> Lista com todos os Objetos já persistidos.
     */
    public List<T> findAll() {
        LOGGER.debug("Iniciando [FIND-ALL] da Entidade "
                + getDomainClass().getName() + ".");
        final EntityManager entity = this.getEntityManager();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = entity.getCriteriaBuilder().createQuery();

        LOGGER.info("Selecionando todos os Registros de "
                + getDomainClass().getName() + ".");
        criteria.select(criteria.from(this.getDomainClass()));

        LOGGER.debug("Finalizando [FIND-ALL] da Entidade "
                + getDomainClass().getName() + ".");
        return entity.createQuery(criteria).getResultList();
    }

    /**
     * Método que recupera um Objeto apartir de seu identificador.
     *
     * @param identifier identificador do Objeto.
     * @return <T>
     */
    public T findById(final Object identifier) {
        LOGGER.debug("Iniciando [FIND-BY-ID] da Entidade "
                + getDomainClass().getName() + ".");
        final EntityManager entity = this.getEntityManager();

        LOGGER.info("Recuperando registro da Entidade "
                + getDomainClass().getName()
                + " através do ID [" + identifier + "]");
        final T objeto = (T) entity.find(this.getDomainClass(), identifier);

        LOGGER.debug("Finalizando [FIND-BY-ID] da Entidade "
                + getDomainClass().getName() + ".");
        return objeto;
    }

    /**
     * Método que recupera um Objeto apartir de um campo qualquer com um
     * valor qualquer.
     *
     * @param fieldName Nome do Campo a ser utilizado para o filtro.
     * @param fieldType Classe que representa o tipo de Valor do Campo.
     * @param fieldValue Valor a ser utilizado para filtrar.
     * @return <T>
     */
    public T findByField(final String fieldName, final Class<?> fieldType,
            final String fieldValue) {
        LOGGER.debug("Iniciando [FIND-BY-FIELD] da Entidade "
                + getDomainClass().getName() + ".");

        LOGGER.debug("Criando Instância de CriteriaBuilder.");
        final CriteriaBuilder cbuilder
                = getEntityManager().getCriteriaBuilder();

        LOGGER.debug("Criando Instância de CriteriaQuery.");
        final CriteriaQuery criteria = cbuilder.createQuery(getDomainClass());

        LOGGER.debug("Criando Instância de Root.");
        final Root<T> root = criteria.from(getDomainClass());

        LOGGER.debug("Definindo Query de Seleção.");
        criteria.where(cbuilder.equal(root
                .get(root.getModel()
                .getSingularAttribute(fieldName, fieldType)), fieldValue));

        T typed = null;
        try {
            LOGGER.info("Recuperando registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Campo [" + fieldName + "] com valor ["
                    + fieldValue + "].");
            typed = (T) getEntityManager().createQuery(criteria)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            LOGGER.error("Erro ao recuperar Registro da Entidade "
                    + getDomainClass().getName()
                    + " através do Campo [" + fieldName + "] com valor ["
                    + fieldValue + "].", ex);
        }

        LOGGER.debug("Finalizando [FIND-BY-FIELD] da Entidade "
                + getDomainClass().getName() + ".");
        return typed;
    }
}