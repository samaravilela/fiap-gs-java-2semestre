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
            "SELECT a FROM AulaCurso a ORDER BY a.titulo", AulaCurso.class);
        List<AulaCurso> aulas = query.getResultList();
        
        System.out.println("DAO - Total de aulas encontradas: " + aulas.size());
        
        // Para cada aula, buscar o CURSO_ID usando query nativa e setar no transient
        for (AulaCurso aula : aulas) {
            try {
                Object cursoIdObj = entityManager.createNativeQuery(
                    "SELECT CURSO_ID FROM T_ZYNT_AULAS_CURSO WHERE ID = ?")
                    .setParameter(1, aula.getId())
                    .getSingleResult();
                if (cursoIdObj != null) {
                    aula.setCursoId(((Number) cursoIdObj).longValue());
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar cursoId para aula " + aula.getId() + ": " + e.getMessage());
            }
        }
        return aulas;
    }
    
    public List<AulaCurso> buscarPorCursoId(Long cursoId) {
        TypedQuery<AulaCurso> query = entityManager.createQuery(
            "SELECT a FROM AulaCurso a WHERE a.curso.id = :cursoId ORDER BY a.titulo", AulaCurso.class);
        query.setParameter("cursoId", cursoId);
        List<AulaCurso> aulas = query.getResultList();
        // Setar o cursoId para cada aula
        for (AulaCurso aula : aulas) {
            if (aula.getCurso() != null) {
                aula.setCursoId(aula.getCurso().getId());
            }
        }
        return aulas;
    }
    
    public List<AulaCurso> listarAtivas() {
        TypedQuery<AulaCurso> query = entityManager.createQuery(
            "SELECT a FROM AulaCurso a WHERE a.ativo = 'S' ORDER BY a.titulo", AulaCurso.class);
        List<AulaCurso> aulas = query.getResultList();
        // Setar o cursoId para cada aula
        for (AulaCurso aula : aulas) {
            if (aula.getCurso() != null) {
                aula.setCursoId(aula.getCurso().getId());
            }
        }
        return aulas;
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

