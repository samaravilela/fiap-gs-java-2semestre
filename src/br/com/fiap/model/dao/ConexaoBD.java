package br.com.fiap.model.dao;

import br.com.fiap.exception.DatabaseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Classe responsável por gerenciar conexões com o banco de dados
 * Implementa padrão Singleton para garantir uma única instância de conexão
 * Adaptado para usar EntityManager do JPA/Hibernate
 */
@ApplicationScoped
public class ConexaoBD {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * Obtém o EntityManager para operações de banco de dados
     * @return EntityManager
     */
    public EntityManager getConexao() {
        if (entityManager == null) {
            throw new DatabaseException("EntityManager não está disponível");
        }
        return entityManager;
    }
    
    /**
     * Realiza commit da transação
     */
    @Transactional
    public void commit() {
        // Commit é automático ao final do método @Transactional
    }
    
    /**
     * Realiza rollback da transação
     */
    @Transactional
    public void rollback() {
        // Rollback é automático em caso de exceção
        throw new DatabaseException("Rollback da transação");
    }
    
    /**
     * Fecha a conexão com o banco de dados
     * No JPA, o EntityManager é gerenciado pelo container
     */
    public void fecharConexao() {
        // No JPA, o EntityManager é gerenciado automaticamente
    }
    
    /**
     * Testa a conexão com o banco de dados
     * @return true se a conexão foi bem-sucedida, false caso contrário
     */
    public boolean testarConexao() {
        try {
            if (entityManager != null) {
                entityManager.createNativeQuery("SELECT 1").getSingleResult();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

