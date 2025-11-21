package br.com.fiap.model.dao;

import br.com.fiap.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO (Data Access Object) para operações de banco de dados relacionadas a Usuario
 * Implementa o padrão DAO para separação de responsabilidades
 */
@ApplicationScoped
public class UsuarioDAO {
    
    @PersistenceContext
    EntityManager entityManager;
    
    /**
     * Cria um novo usuário
     */
    public void criar(Usuario usuario) {
        entityManager.persist(usuario);
    }
    
    /**
     * Busca usuário por ID
     */
    public Usuario buscarPorId(Long id) {
        return entityManager.find(Usuario.class, id);
    }
    
    /**
     * Busca usuário por email
     */
    public Usuario buscarPorEmail(String email) {
        try {
            TypedQuery<Usuario> query = entityManager.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Lista todos os usuários
     */
    public List<Usuario> listarTodos() {
        TypedQuery<Usuario> query = entityManager.createQuery(
            "SELECT u FROM Usuario u ORDER BY u.nome", Usuario.class);
        return query.getResultList();
    }
    
    /**
     * Atualiza um usuário
     */
    public void atualizar(Usuario usuario) {
        entityManager.merge(usuario);
    }
    
    /**
     * Remove um usuário
     */
    public void remover(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            entityManager.remove(usuario);
        }
    }
    
    /**
     * Verifica se email já existe
     */
    public boolean emailExiste(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM Usuario u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }
}

