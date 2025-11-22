package br.com.fiap.model.dao;

import br.com.fiap.model.entity.Mentoria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MentoriaDAO {
    
    @Inject
    EntityManager entityManager;
    
    public void criar(Mentoria mentoria) {
        entityManager.persist(mentoria);
        entityManager.flush();
    }
    
    public Mentoria buscarPorId(Long id) {
        TypedQuery<Mentoria> query = entityManager.createQuery(
            "SELECT m FROM Mentoria m " +
            "LEFT JOIN FETCH m.tutor " +
            "LEFT JOIN FETCH m.usuario " +
            "WHERE m.id = :id", Mentoria.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }
    
    public List<Mentoria> listarTodos() {
        TypedQuery<Mentoria> query = entityManager.createQuery(
            "SELECT DISTINCT m FROM Mentoria m " +
            "LEFT JOIN FETCH m.tutor " +
            "LEFT JOIN FETCH m.usuario " +
            "ORDER BY m.data DESC", Mentoria.class);
        return query.getResultList();
    }
    
    public List<Mentoria> buscarPorData(LocalDate data) {
        TypedQuery<Mentoria> query = entityManager.createQuery(
            "SELECT DISTINCT m FROM Mentoria m " +
            "LEFT JOIN FETCH m.tutor " +
            "LEFT JOIN FETCH m.usuario " +
            "WHERE m.data = :data ORDER BY m.dataCriacao", Mentoria.class);
        query.setParameter("data", data);
        return query.getResultList();
    }
    
    public List<Mentoria> buscarPorEmail(String email) {
        TypedQuery<Mentoria> query = entityManager.createQuery(
            "SELECT DISTINCT m FROM Mentoria m " +
            "LEFT JOIN FETCH m.tutor " +
            "LEFT JOIN FETCH m.usuario " +
            "WHERE m.email = :email ORDER BY m.data DESC", Mentoria.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
    
    public void atualizar(Mentoria mentoria) {
        entityManager.merge(mentoria);
        entityManager.flush();
    }
    
    public void remover(Long id) {
        Mentoria mentoria = buscarPorId(id);
        if (mentoria != null) {
            entityManager.remove(mentoria);
            entityManager.flush();
        }
    }
}

