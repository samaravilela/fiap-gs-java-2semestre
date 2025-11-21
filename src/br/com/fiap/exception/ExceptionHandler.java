package br.com.fiap.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Tratamento global de exceções
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
    
    @Override
    public Response toResponse(Exception exception) {
        Map<String, Object> response = new HashMap<>();
        
        if (exception instanceof BusinessRuleException) {
            response.put("erro", exception.getMessage());
            response.put("tipo", "BusinessRuleException");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response)
                    .build();
        }
        
        if (exception instanceof ResourceNotFoundException) {
            response.put("erro", exception.getMessage());
            response.put("tipo", "ResourceNotFoundException");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(response)
                    .build();
        }
        
        if (exception instanceof ValidationException) {
            response.put("erro", exception.getMessage());
            response.put("tipo", "ValidationException");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response)
                    .build();
        }
        
        if (exception instanceof DatabaseException) {
            response.put("erro", "Erro ao acessar banco de dados");
            response.put("tipo", "DatabaseException");
            response.put("detalhes", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(response)
                    .build();
        }
        
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) exception;
            String mensagens = cve.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            
            response.put("erro", mensagens);
            response.put("tipo", "ValidationException");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response)
                    .build();
        }
        
        response.put("erro", "Erro interno do servidor");
        response.put("tipo", "InternalServerError");
        response.put("detalhes", exception.getMessage());
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
    }
}

