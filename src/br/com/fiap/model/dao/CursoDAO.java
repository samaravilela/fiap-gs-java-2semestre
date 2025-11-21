package br.com.fiap.model.dao;

import br.com.fiap.model.entity.Curso;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CursoDAO {
    
    @Inject
    EntityManager entityManager;
    
    public void criar(Curso curso) {
        entityManager.persist(curso);
        entityManager.flush();
    }
    
    public Curso buscarPorId(Long id) {
        return entityManager.find(Curso.class, id);
    }
    
    public List<Curso> listarTodos() {
        TypedQuery<Curso> query = entityManager.createQuery(
            "SELECT c FROM Curso c ORDER BY c.titulo", Curso.class);
        return query.getResultList();
    }
    
    public List<Curso> listarAtivos() {
        TypedQuery<Curso> query = entityManager.createQuery(
            "SELECT c FROM Curso c WHERE c.ativo = 'S' ORDER BY c.titulo", Curso.class);
        return query.getResultList();
    }
    
    public void atualizar(Curso curso) {
        entityManager.merge(curso);
        entityManager.flush();
    }
    
    public void remover(Long id) {
        Curso curso = buscarPorId(id);
        if (curso != null) {
            entityManager.remove(curso);
            entityManager.flush();
        }
    }
}

