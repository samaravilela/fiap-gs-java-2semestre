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
            System.out.println("=== AulaCursoDAO.listarTodos() INICIADO ===");
            
            // Tentar JPQL primeiro (como os outros DAOs)
            TypedQuery<AulaCurso> query = entityManager.createQuery(
                "SELECT DISTINCT a FROM AulaCurso a LEFT JOIN FETCH a.curso ORDER BY a.titulo", 
                AulaCurso.class);
            
            List<AulaCurso> result = query.getResultList();
            
            System.out.println("JPQL retornou: " + result.size() + " aulas");
            
            // Garantir que cursoId está setado
            for (AulaCurso aula : result) {
                if (aula.getCurso() != null && aula.getCursoId() == null) {
                    aula.setCursoId(aula.getCurso().getId());
                }
                System.out.println("  - Aula ID=" + aula.getId() + ", Título=" + aula.getTitulo() + ", CursoId=" + aula.getCursoId());
            }
            
            // Se JPQL retornou vazio, tentar native query
            if (result == null || result.isEmpty()) {
                System.out.println("JPQL retornou vazio, tentando native query...");
                return listarTodosNative();
            }
            
            System.out.println("=== AulaCursoDAO.listarTodos() FINALIZADO - Retornando " + result.size() + " aulas ===");
            return result;
            
        } catch (Exception e) {
            System.err.println("ERRO em listarTodos JPQL: " + e.getMessage());
            e.printStackTrace();
            System.out.println("Tentando native query como fallback...");
            return listarTodosNative();
        }
    }
    
    private List<AulaCurso> listarTodosNative() {
        try {
            System.out.println("=== Executando Native Query ===");
            
            String sql = "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO ORDER BY TITULO";
            
            @SuppressWarnings("unchecked")
            List<Object[]> rows = entityManager.createNativeQuery(sql).getResultList();
            
            System.out.println("Native Query retornou: " + rows.size() + " linhas");
            
            List<AulaCurso> aulas = new java.util.ArrayList<>();
            
            for (Object[] row : rows) {
                try {
                    AulaCurso aula = new AulaCurso();
                    
                    aula.setId(((Number) row[0]).longValue());
                    
                    if (row[1] != null) {
                        Long cursoId = ((Number) row[1]).longValue();
                        aula.setCursoId(cursoId);
                    }
                    
                    aula.setTitulo((String) row[2]);
                    aula.setDescricao((String) row[3]);
                    aula.setUrl((String) row[4]);
                    aula.setAtivo(row[5] != null ? (String) row[5] : "S");
                    
                    if (row[6] != null) {
                        if (row[6] instanceof java.sql.Timestamp) {
                            aula.setDataCriacao(((java.sql.Timestamp) row[6]).toLocalDateTime());
                        } else if (row[6] instanceof java.time.LocalDateTime) {
                            aula.setDataCriacao((java.time.LocalDateTime) row[6]);
                        }
                    }
                    
                    if (row[7] != null) {
                        if (row[7] instanceof java.sql.Timestamp) {
                            aula.setDataAtualizacao(((java.sql.Timestamp) row[7]).toLocalDateTime());
                        } else if (row[7] instanceof java.time.LocalDateTime) {
                            aula.setDataAtualizacao((java.time.LocalDateTime) row[7]);
                        }
                    }
                    
                    aulas.add(aula);
                    System.out.println("  ✓ Convertida: ID=" + aula.getId() + ", Título=" + aula.getTitulo() + ", CursoId=" + aula.getCursoId());
                    
                } catch (Exception e) {
                    System.err.println("  ✗ Erro ao converter: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
            System.out.println("Total convertido: " + aulas.size());
            return aulas;
            
        } catch (Exception e) {
            System.err.println("ERRO FATAL em native query: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    public List<AulaCurso> buscarPorCursoId(Long cursoId) {
        try {
            TypedQuery<AulaCurso> query = entityManager.createQuery(
                "SELECT DISTINCT a FROM AulaCurso a LEFT JOIN FETCH a.curso WHERE a.curso.id = :cursoId ORDER BY a.titulo", 
                AulaCurso.class);
            query.setParameter("cursoId", cursoId);
            
            List<AulaCurso> result = query.getResultList();
            
            for (AulaCurso aula : result) {
                if (aula.getCurso() != null && aula.getCursoId() == null) {
                    aula.setCursoId(aula.getCurso().getId());
                }
            }
            
            return result;
        } catch (Exception e) {
            System.err.println("ERRO em buscarPorCursoId: " + e.getMessage());
            return buscarPorCursoIdNative(cursoId);
        }
    }
    
    private List<AulaCurso> buscarPorCursoIdNative(Long cursoId) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(
            "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO WHERE CURSO_ID = ? ORDER BY TITULO")
            .setParameter(1, cursoId)
            .getResultList();
        
        return converterRows(rows);
    }
    
    public List<AulaCurso> listarAtivas() {
        try {
            TypedQuery<AulaCurso> query = entityManager.createQuery(
                "SELECT DISTINCT a FROM AulaCurso a LEFT JOIN FETCH a.curso WHERE a.ativo = 'S' ORDER BY a.titulo", 
                AulaCurso.class);
            
            List<AulaCurso> result = query.getResultList();
            
            for (AulaCurso aula : result) {
                if (aula.getCurso() != null && aula.getCursoId() == null) {
                    aula.setCursoId(aula.getCurso().getId());
                }
            }
            
            return result;
        } catch (Exception e) {
            System.err.println("ERRO em listarAtivas: " + e.getMessage());
            return listarAtivasNative();
        }
    }
    
    private List<AulaCurso> listarAtivasNative() {
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
                AulaCurso aula = new AulaCurso();
                aula.setId(((Number) row[0]).longValue());
                
                if (row[1] != null) {
                    Long cursoId = ((Number) row[1]).longValue();
                    aula.setCursoId(cursoId);
                }
                
                aula.setTitulo((String) row[2]);
                aula.setDescricao((String) row[3]);
                aula.setUrl((String) row[4]);
                aula.setAtivo(row[5] != null ? (String) row[5] : "S");
                
                if (row[6] != null) {
                    if (row[6] instanceof java.sql.Timestamp) {
                        aula.setDataCriacao(((java.sql.Timestamp) row[6]).toLocalDateTime());
                    } else if (row[6] instanceof java.time.LocalDateTime) {
                        aula.setDataCriacao((java.time.LocalDateTime) row[6]);
                    }
                }
                
                if (row[7] != null) {
                    if (row[7] instanceof java.sql.Timestamp) {
                        aula.setDataAtualizacao(((java.sql.Timestamp) row[7]).toLocalDateTime());
                    } else if (row[7] instanceof java.time.LocalDateTime) {
                        aula.setDataAtualizacao((java.time.LocalDateTime) row[7]);
                    }
                }
                
                aulas.add(aula);
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
