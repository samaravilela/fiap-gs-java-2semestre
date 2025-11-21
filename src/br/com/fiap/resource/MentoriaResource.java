package br.com.fiap.resource;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.Mentoria;
import br.com.fiap.service.MentoriaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/mentorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MentoriaResource {
    
    @Inject
    MentoriaService mentoriaService;
    
    @POST
    public Response criar(@Valid Mentoria mentoria) {
        try {
            Mentoria novaMentoria = mentoriaService.criar(mentoria);
            return Response.status(Response.Status.CREATED)
                    .entity(novaMentoria)
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
    public Response listarTodos() {
        try {
            List<Mentoria> mentorias = mentoriaService.listarTodos();
            return Response.ok(mentorias).build();
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
            Mentoria mentoria = mentoriaService.buscarPorId(id);
            return Response.ok(mentoria).build();
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
    public Response atualizar(@PathParam("id") Long id, @Valid Mentoria mentoria) {
        try {
            mentoria.setId(id);
            mentoriaService.atualizar(mentoria);
            Mentoria mentoriaAtualizada = mentoriaService.buscarPorId(id);
            return Response.ok(mentoriaAtualizada).build();
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
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            mentoriaService.deletar(id);
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
    
    @POST
    @Path("/{id}/cancelar")
    public Response cancelar(@PathParam("id") Long id) {
        try {
            mentoriaService.cancelar(id);
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
}
