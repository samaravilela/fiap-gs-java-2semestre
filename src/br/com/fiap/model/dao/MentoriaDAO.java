package br.com.fiap.model.dao;

import br.com.fiap.model.entity.Mentoria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MentoriaDAO {
    
    @PersistenceContext
    EntityManager entityManager;
    
    public void criar(Mentoria mentoria) {
        entityManager.persist(mentoria);
    }
    
    public Mentoria buscarPorId(Long id) {
        return entityManager.find(Mentoria.class, id);
    }
    
    public List<Mentoria> listarTodos() {
        TypedQuery<Mentoria> query = entityManager.createQuery(
            "SELECT m FROM Mentoria m ORDER BY m.data DESC", Mentoria.class);
        return query.getResultList();
    }
    
    public List<Mentoria> buscarPorData(LocalDate data) {
        TypedQuery<Mentoria> query = entityManager.createQuery(
            "SELECT m FROM Mentoria m WHERE m.data = :data ORDER BY m.dataCriacao", Mentoria.class);
        query.setParameter("data", data);
        return query.getResultList();
    }
    
    public List<Mentoria> buscarPorEmail(String email) {
        TypedQuery<Mentoria> query = entityManager.createQuery(
            "SELECT m FROM Mentoria m WHERE m.email = :email ORDER BY m.data DESC", Mentoria.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
    
    public void atualizar(Mentoria mentoria) {
        entityManager.merge(mentoria);
    }
    
    public void remover(Long id) {
        Mentoria mentoria = buscarPorId(id);
        if (mentoria != null) {
            entityManager.remove(mentoria);
        }
    }
}

