package br.com.fiap.model.dao;

import br.com.fiap.model.entity.AulaCurso;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AulaCursoDAO {
    
    @Inject
    EntityManager entityManager;
    
    public void criar(AulaCurso aulaCurso) {
        entityManager.persist(aulaCurso);
        entityManager.flush();
    }
    
    public AulaCurso buscarPorId(Long id) {
        return entityManager.find(AulaCurso.class, id);
    }
    
    public List<AulaCurso> listarTodos() {
        TypedQuery<AulaCurso> query = entityManager.createQuery(
            "SELECT DISTINCT a FROM AulaCurso a LEFT JOIN FETCH a.curso ORDER BY a.titulo", AulaCurso.class);
        return query.getResultList();
    }
    
    public List<AulaCurso> buscarPorCursoId(Long cursoId) {
        TypedQuery<AulaCurso> query = entityManager.createQuery(
            "SELECT DISTINCT a FROM AulaCurso a LEFT JOIN FETCH a.curso WHERE a.curso.id = :cursoId ORDER BY a.titulo", AulaCurso.class);
        query.setParameter("cursoId", cursoId);
        return query.getResultList();
    }
    
    public List<AulaCurso> listarAtivas() {
        TypedQuery<AulaCurso> query = entityManager.createQuery(
            "SELECT DISTINCT a FROM AulaCurso a LEFT JOIN FETCH a.curso WHERE a.ativo = 'S' ORDER BY a.titulo", AulaCurso.class);
        return query.getResultList();
    }
    
    public void atualizar(AulaCurso aulaCurso) {
        entityManager.merge(aulaCurso);
        entityManager.flush();
    }
    
    public void remover(Long id) {
        AulaCurso aulaCurso = buscarPorId(id);
        if (aulaCurso != null) {
            entityManager.remove(aulaCurso);
            entityManager.flush();
        }
    }
}

