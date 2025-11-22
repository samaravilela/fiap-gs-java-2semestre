package br.com.fiap.resource;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.Usuario;
import br.com.fiap.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Resource REST para operações de Usuario
 * Segue os princípios REST com verbos HTTP apropriados
 */
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    
    @Inject
    UsuarioService usuarioService;
    
    /**
     * POST /api/usuarios - Criar novo usuário
     */
    @POST
    public Response criar(@Valid Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.criar(usuario);
            // Senha não será serializada devido ao @JsonIgnore na entidade
            return Response.status(Response.Status.CREATED)
                    .entity(novoUsuario)
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
    
    /**
     * POST /api/usuarios/login - Autenticar usuário
     */
    @POST
    @Path("/login")
    public Response login(Map<String, String> credenciais) {
        try {
            String email = credenciais.get("email");
            String senha = credenciais.get("senha");
            
            Usuario usuario = usuarioService.autenticar(email, senha);
            // Senha não será serializada devido ao @JsonIgnore na entidade
            
            Map<String, Object> response = new HashMap<>();
            response.put("usuario", usuario);
            response.put("token", "token-jwt-aqui"); // Em produção, gerar JWT real
            
            return Response.ok(response).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
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
    
    /**
     * GET /api/usuarios - Listar todos os usuários
     */
    @GET
    public Response listarTodos() {
        try {
            java.util.List<Usuario> usuarios = usuarioService.listarTodos();
            // Senhas não serão serializadas devido ao @JsonIgnore na entidade
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * GET /api/usuarios/{id} - Buscar usuário por ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            // Senha não será serializada devido ao @JsonIgnore na entidade
            return Response.ok(usuario).build();
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
    public Response atualizar(@PathParam("id") Long id, @Valid Usuario usuario) {
        try {
            usuario.setId(id);
            usuarioService.atualizar(usuario);
            Usuario usuarioAtualizado = usuarioService.buscarPorId(id);
            // Senha não será serializada devido ao @JsonIgnore na entidade
            return Response.ok(usuarioAtualizado).build();
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
            usuarioService.deletar(id);
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

