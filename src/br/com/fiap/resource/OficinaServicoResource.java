package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.OficinaServico;
import br.com.fiap.service.OficinaServicoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/oficina-servicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OficinaServicoResource {
    
    @Inject
    OficinaServicoService oficinaServicoService;
    
    @POST
    public Response criar(@Valid OficinaServico oficinaServico) {
        try {
            OficinaServico novoServico = oficinaServicoService.criar(oficinaServico);
            return Response.status(Response.Status.CREATED)
                    .entity(novoServico)
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
    public Response listarTodos(@QueryParam("oficinaId") Long oficinaId, @QueryParam("ativos") Boolean ativos) {
        try {
            List<OficinaServico> servicos;
            if (oficinaId != null) {
                servicos = oficinaServicoService.buscarPorOficinaId(oficinaId);
            } else if (ativos != null && ativos) {
                servicos = oficinaServicoService.listarAtivos();
            } else {
                servicos = oficinaServicoService.listarTodos();
            }
            return Response.ok(servicos).build();
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
            OficinaServico oficinaServico = oficinaServicoService.buscarPorId(id);
            return Response.ok(oficinaServico).build();
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
    public Response atualizar(@PathParam("id") Long id, @Valid OficinaServico oficinaServico) {
        try {
            oficinaServico.setId(id);
            oficinaServicoService.atualizar(oficinaServico);
            OficinaServico servicoAtualizado = oficinaServicoService.buscarPorId(id);
            return Response.ok(servicoAtualizado).build();
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
            oficinaServicoService.deletar(id);
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

