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
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> rows = entityManager.createNativeQuery(
                "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO ORDER BY TITULO")
                .getResultList();
            
            System.out.println("AulaCursoDAO.listarTodos() - Linhas retornadas do banco: " + rows.size());
            
            List<AulaCurso> aulas = converterRows(rows);
            
            System.out.println("AulaCursoDAO.listarTodos() - Aulas convertidas: " + aulas.size());
            
            return aulas;
        } catch (Exception e) {
            System.err.println("ERRO em listarTodos: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    public List<AulaCurso> buscarPorCursoId(Long cursoId) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(
            "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO WHERE CURSO_ID = ? ORDER BY TITULO")
            .setParameter(1, cursoId)
            .getResultList();
        
        return converterRows(rows);
    }
    
    public List<AulaCurso> listarAtivas() {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(
            "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO WHERE ATIVO = 'S' ORDER BY TITULO")
            .getResultList();
        
        return converterRows(rows);
    }
    
    private List<AulaCurso> converterRows(List<Object[]> rows) {
        List<AulaCurso> aulas = new java.util.ArrayList<>();
        for (Object[] row : rows) {
            try {
                AulaCurso a = new AulaCurso();
                a.setId(((Number) row[0]).longValue());
                
                // IMPORTANTE: Setar cursoId usando setCursoId para popular cursoIdTransient
                Long cursoId = row[1] != null ? ((Number) row[1]).longValue() : null;
                if (cursoId != null) {
                    a.setCursoId(cursoId);
                }
                
                a.setTitulo((String) row[2]);
                a.setDescricao((String) row[3]);
                a.setUrl(row[4] != null ? (String) row[4] : null);
                a.setAtivo(row[5] != null ? (String) row[5] : "S");
                
                if (row[6] != null) {
                    if (row[6] instanceof java.sql.Timestamp) {
                        a.setDataCriacao(((java.sql.Timestamp) row[6]).toLocalDateTime());
                    } else if (row[6] instanceof java.time.LocalDateTime) {
                        a.setDataCriacao((java.time.LocalDateTime) row[6]);
                    }
                }
                if (row[7] != null) {
                    if (row[7] instanceof java.sql.Timestamp) {
                        a.setDataAtualizacao(((java.sql.Timestamp) row[7]).toLocalDateTime());
                    } else if (row[7] instanceof java.time.LocalDateTime) {
                        a.setDataAtualizacao((java.time.LocalDateTime) row[7]);
                    }
                }
                
                aulas.add(a);
            } catch (Exception e) {
                System.err.println("Erro ao converter linha: " + e.getMessage());
                e.printStackTrace();
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

