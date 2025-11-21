package br.com.fiap.model.dao;

import br.com.fiap.model.entity.Tutor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TutorDAO {
    
    @Inject
    EntityManager entityManager;
    
    public void criar(Tutor tutor) {
        entityManager.persist(tutor);
        entityManager.flush();
    }
    
    public Tutor buscarPorId(Long id) {
        return entityManager.find(Tutor.class, id);
    }
    
    public List<Tutor> listarTodos() {
        TypedQuery<Tutor> query = entityManager.createQuery(
            "SELECT t FROM Tutor t ORDER BY t.nome", Tutor.class);
        return query.getResultList();
    }
    
    public List<Tutor> listarAtivos() {
        TypedQuery<Tutor> query = entityManager.createQuery(
            "SELECT t FROM Tutor t WHERE t.ativo = 'S' ORDER BY t.nome", Tutor.class);
        return query.getResultList();
    }
    
    public void atualizar(Tutor tutor) {
        entityManager.merge(tutor);
        entityManager.flush();
    }
    
    public void remover(Long id) {
        Tutor tutor = buscarPorId(id);
        if (tutor != null) {
            entityManager.remove(tutor);
            entityManager.flush();
        }
    }
}

