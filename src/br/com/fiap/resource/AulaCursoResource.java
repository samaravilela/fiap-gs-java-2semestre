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

@Path("/aulas-curso")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AulaCursoResource {
    
    @Inject
    AulaCursoService aulaCursoService;
    
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
    public Response listarTodos(@QueryParam("cursoId") Long cursoId, @QueryParam("ativos") Boolean ativos) {
        try {
            List<AulaCurso> aulas;
            if (cursoId != null) {
                aulas = aulaCursoService.buscarPorCursoId(cursoId);
            } else if (ativos != null && ativos) {
                aulas = aulaCursoService.listarAtivas();
            } else {
                aulas = aulaCursoService.listarTodos();
            }
            return Response.ok(aulas).build();
        } catch (Exception e) {
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

