package br.com.fiap.resource;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.Oficina;
import br.com.fiap.model.dao.OficinaDAO;
import br.com.fiap.service.OficinaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/oficinas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OficinaResource {
    
    @Inject
    OficinaService oficinaService;
    
    @Inject
    OficinaDAO oficinaDAO;
    
    @POST
    public Response criar(@Valid Oficina oficina) {
        try {
            Oficina novaOficina = oficinaService.criar(oficina);
            return Response.status(Response.Status.CREATED)
                    .entity(novaOficina)
                    .build();
        } catch (ValidationException | BusinessRuleException e) {
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
    public Response listarTodos(@QueryParam("busca") String busca) {
        try {
            List<Oficina> oficinas;
            if (busca != null && !busca.trim().isEmpty()) {
                oficinas = oficinaService.buscarPorTermo(busca);
            } else {
                oficinas = oficinaService.listarTodos();
            }
            return Response.ok(oficinas).build();
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
            Oficina oficina = oficinaService.buscarPorId(id);
            return Response.ok(oficina).build();
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
    
    @GET
    @Path("/cidade/{cidade}")
    public Response buscarPorCidade(@PathParam("cidade") String cidade) {
        try {
            List<Oficina> oficinas = oficinaDAO.buscarPorCidade(cidade);
            return Response.ok(oficinas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    @GET
    @Path("/estado/{estado}")
    public Response buscarPorEstado(@PathParam("estado") String estado) {
        try {
            List<Oficina> oficinas = oficinaDAO.buscarPorEstado(estado);
            return Response.ok(oficinas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid Oficina oficina) {
        try {
            oficina.setId(id);
            oficinaService.atualizar(oficina);
            Oficina oficinaAtualizada = oficinaService.buscarPorId(id);
            return Response.ok(oficinaAtualizada).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (ValidationException | BusinessRuleException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    @POST
    @Path("/{id}/aprovar")
    public Response aprovar(@PathParam("id") Long id) {
        try {
            oficinaService.aprovar(id);
            return Response.ok().build();
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
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            oficinaService.deletar(id);
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
