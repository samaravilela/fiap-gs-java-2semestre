package br.com.fiap.model.dao;

import br.com.fiap.model.entity.AulaCurso;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
        // Usar query nativa e mapear manualmente
        Query query = entityManager.createNativeQuery(
            "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO ORDER BY TITULO");
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        
        return results.stream().map(row -> {
            AulaCurso aula = new AulaCurso();
            aula.setId(((Number) row[0]).longValue());
            // Setar o cursoId no campo transient para serialização
            Long cursoIdValue = ((Number) row[1]).longValue();
            aula.setCursoId(cursoIdValue);
            aula.setTitulo((String) row[2]);
            aula.setDescricao((String) row[3]);
            aula.setUrl((String) row[4]);
            aula.setAtivo((String) row[5]);
            if (row[6] != null) {
                aula.setDataCriacao(((java.sql.Timestamp) row[6]).toLocalDateTime());
            }
            if (row[7] != null) {
                aula.setDataAtualizacao(((java.sql.Timestamp) row[7]).toLocalDateTime());
            }
            return aula;
        }).collect(Collectors.toList());
    }
    
    public List<AulaCurso> buscarPorCursoId(Long cursoId) {
        Query query = entityManager.createNativeQuery(
            "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO WHERE CURSO_ID = ? ORDER BY TITULO");
        query.setParameter(1, cursoId);
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        
        return results.stream().map(row -> {
            AulaCurso aula = new AulaCurso();
            aula.setId(((Number) row[0]).longValue());
            Long cursoIdValue = ((Number) row[1]).longValue();
            aula.setCursoId(cursoIdValue);
            aula.setTitulo((String) row[2]);
            aula.setDescricao((String) row[3]);
            aula.setUrl((String) row[4]);
            aula.setAtivo((String) row[5]);
            if (row[6] != null) {
                aula.setDataCriacao(((java.sql.Timestamp) row[6]).toLocalDateTime());
            }
            if (row[7] != null) {
                aula.setDataAtualizacao(((java.sql.Timestamp) row[7]).toLocalDateTime());
            }
            return aula;
        }).collect(Collectors.toList());
    }
    
    public List<AulaCurso> listarAtivas() {
        Query query = entityManager.createNativeQuery(
            "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO WHERE ATIVO = 'S' ORDER BY TITULO");
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        
        return results.stream().map(row -> {
            AulaCurso aula = new AulaCurso();
            aula.setId(((Number) row[0]).longValue());
            Long cursoIdValue = ((Number) row[1]).longValue();
            aula.setCursoId(cursoIdValue);
            aula.setTitulo((String) row[2]);
            aula.setDescricao((String) row[3]);
            aula.setUrl((String) row[4]);
            aula.setAtivo((String) row[5]);
            if (row[6] != null) {
                aula.setDataCriacao(((java.sql.Timestamp) row[6]).toLocalDateTime());
            }
            if (row[7] != null) {
                aula.setDataAtualizacao(((java.sql.Timestamp) row[7]).toLocalDateTime());
            }
            return aula;
        }).collect(Collectors.toList());
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

