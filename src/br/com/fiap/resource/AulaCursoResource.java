package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.AulaCurso;
import br.com.fiap.service.AulaCursoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@Path("/aulas-curso")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AulaCursoResource {
    
    @Inject
    AulaCursoService aulaCursoService;
    
    @Inject
    jakarta.persistence.EntityManager entityManager;
    
    @POST
    public Response criar(@Valid AulaCurso aulaCurso) {
        try {
            AulaCurso novaAula = aulaCursoService.criar(aulaCurso);
            return Response.status(Response.Status.CREATED)
                    .entity(novaAula)
                    .build();
        } catch (ValidationException | ResourceNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    @GET
    @Path("/teste")
    public Response teste() {
        // Endpoint de teste para verificar se o Resource funciona
        Map<String, Object> teste = new HashMap<>();
        teste.put("mensagem", "Endpoint funcionando!");
        teste.put("timestamp", java.time.LocalDateTime.now().toString());
        return Response.ok(teste).build();
    }
    
    @GET
    public Response listarTodos(@QueryParam("cursoId") Long cursoId, @QueryParam("ativos") Boolean ativos) {
        try {
            System.out.println("========================================");
            System.out.println("AulaCursoResource.listarTodos() - BUSCA DIRETA NO BANCO");
            System.out.println("cursoId: " + cursoId + ", ativos: " + ativos);
            System.out.println("========================================");
            
            // SEMPRE fazer busca direta no banco para garantir que funcione
            String sql = "SELECT ID, CURSO_ID, TITULO, DESCRICAO, URL, ATIVO, DATA_CRIACAO, DATA_ATUALIZACAO FROM T_ZYNT_AULAS_CURSO";
            
            if (cursoId != null) {
                sql += " WHERE CURSO_ID = :cursoId";
            } else if (ativos != null && ativos) {
                sql += " WHERE ATIVO = 'S'";
            }
            sql += " ORDER BY TITULO";
            
            System.out.println("SQL: " + sql);
            
            jakarta.persistence.Query query = entityManager.createNativeQuery(sql);
            if (cursoId != null) {
                query.setParameter("cursoId", cursoId);
            }
            
            @SuppressWarnings("unchecked")
            List<Object[]> rows = query.getResultList();
            
            System.out.println("✓ Query retornou " + rows.size() + " linhas do banco");
            
            List<Map<String, Object>> aulasMap = new ArrayList<>();
            
            for (Object[] row : rows) {
                try {
                    Map<String, Object> aulaMap = new HashMap<>();
                    
                    // ID - índice 0
                    if (row[0] != null) {
                        aulaMap.put("id", ((Number) row[0]).longValue());
                    }
                    // CURSO_ID - índice 1
                    if (row[1] != null) {
                        aulaMap.put("cursoId", ((Number) row[1]).longValue());
                    }
                    // TITULO - índice 2
                    if (row[2] != null) {
                        aulaMap.put("titulo", (String) row[2]);
                    }
                    // DESCRICAO - índice 3
                    if (row[3] != null) {
                        aulaMap.put("descricao", (String) row[3]);
                    }
                    // URL - índice 4
                    if (row[4] != null) {
                        String url = String.valueOf(row[4]);
                        if (!"null".equalsIgnoreCase(url) && !url.trim().isEmpty()) {
                            aulaMap.put("url", url);
                        }
                    }
                    // ATIVO - índice 5
                    aulaMap.put("ativo", row[5] != null ? (String) row[5] : "S");
                    
                    // DATA_CRIACAO - índice 6
                    if (row[6] != null) {
                        if (row[6] instanceof java.sql.Timestamp) {
                            aulaMap.put("dataCriacao", ((java.sql.Timestamp) row[6]).toLocalDateTime().toString());
                        } else {
                            aulaMap.put("dataCriacao", row[6].toString());
                        }
                    }
                    // DATA_ATUALIZACAO - índice 7
                    if (row[7] != null) {
                        if (row[7] instanceof java.sql.Timestamp) {
                            aulaMap.put("dataAtualizacao", ((java.sql.Timestamp) row[7]).toLocalDateTime().toString());
                        } else {
                            aulaMap.put("dataAtualizacao", row[7].toString());
                        }
                    }
                    
                    aulasMap.add(aulaMap);
                    System.out.println("  ✓ Map: ID=" + aulaMap.get("id") + ", Título=" + aulaMap.get("titulo") + ", URL=" + (aulaMap.get("url") != null ? "presente" : "null"));
                } catch (Exception e) {
                    System.err.println("  ✗ Erro ao converter linha: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
            System.out.println("✓ Total de Maps criados: " + aulasMap.size());
            System.out.println("========================================");
            
            return Response.ok(aulasMap).build();
            
        } catch (Exception e) {
            System.err.println("✗ ERRO FATAL: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            AulaCurso aulaCurso = aulaCursoService.buscarPorId(id);
            return Response.ok(aulaCurso).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid AulaCurso aulaCurso) {
        try {
            aulaCurso.setId(id);
            aulaCursoService.atualizar(aulaCurso);
            AulaCurso aulaAtualizada = aulaCursoService.buscarPorId(id);
            return Response.ok(aulaAtualizada).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            aulaCursoService.deletar(id);
            return Response.noContent().build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
}
