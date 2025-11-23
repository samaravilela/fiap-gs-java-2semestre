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
        
        // Setar o cursoId para cada aula usando query nativa separada
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
                // Ignora erro e continua
            }
        }
        return aulas;
    }
    
    public List<AulaCurso> buscarPorCursoId(Long cursoId) {
        // Usar query nativa para buscar diretamente pelo CURSO_ID
        @SuppressWarnings("unchecked")
        List<Long> ids = entityManager.createNativeQuery(
            "SELECT ID FROM T_ZYNT_AULAS_CURSO WHERE CURSO_ID = ?")
            .setParameter(1, cursoId)
            .getResultList();
        
        // Buscar as entidades pelos IDs
        List<AulaCurso> aulas = new java.util.ArrayList<>();
        for (Long id : ids) {
            AulaCurso aula = entityManager.find(AulaCurso.class, id);
            if (aula != null) {
                aula.setCursoId(cursoId);
                aulas.add(aula);
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
            try {
                Object cursoIdObj = entityManager.createNativeQuery(
                    "SELECT CURSO_ID FROM T_ZYNT_AULAS_CURSO WHERE ID = ?")
                    .setParameter(1, aula.getId())
                    .getSingleResult();
                if (cursoIdObj != null) {
                    aula.setCursoId(((Number) cursoIdObj).longValue());
                }
            } catch (Exception e) {
                // Ignora erro e continua
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

