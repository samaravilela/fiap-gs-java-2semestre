package br.com.fiap.resource;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.PontoRecarga;
import br.com.fiap.service.PontoRecargaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pontos-recarga")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PontoRecargaResource {
    
    @Inject
    PontoRecargaService pontoRecargaService;
    
    @POST
    public Response criar(@Valid PontoRecarga pontoRecarga) {
        try {
            PontoRecarga novoPonto = pontoRecargaService.criar(pontoRecarga);
            return Response.status(Response.Status.CREATED)
                    .entity(novoPonto)
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
    
    @GET
    public Response listarTodos(@QueryParam("tipo") String tipo, @QueryParam("busca") String busca) {
        try {
            List<PontoRecarga> pontos;
            if (tipo != null && !tipo.trim().isEmpty()) {
                try {
                    PontoRecarga.TipoRecarga tipoEnum = PontoRecarga.TipoRecarga.valueOf(tipo.toUpperCase());
                    pontos = pontoRecargaService.buscarPorTipo(tipoEnum);
                } catch (IllegalArgumentException e) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Tipo de recarga inválido. Valores aceitos: AC, DC, AC_DC")
                            .build();
                }
            } else if (busca != null && !busca.trim().isEmpty()) {
                pontos = pontoRecargaService.buscarPorTermo(busca);
            } else {
                pontos = pontoRecargaService.listarTodos();
            }
            return Response.ok(pontos).build();
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
            PontoRecarga pontoRecarga = pontoRecargaService.buscarPorId(id);
            return Response.ok(pontoRecarga).build();
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
    public Response atualizar(@PathParam("id") Long id, @Valid PontoRecarga pontoRecarga) {
        try {
            pontoRecarga.setId(id);
            pontoRecargaService.atualizar(pontoRecarga);
            PontoRecarga pontoAtualizado = pontoRecargaService.buscarPorId(id);
            return Response.ok(pontoAtualizado).build();
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
            pontoRecargaService.deletar(id);
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

