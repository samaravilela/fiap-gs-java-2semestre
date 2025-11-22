package br.com.fiap.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.annotation.Priority;
import java.io.IOException;

/**
 * Filtro CORS para permitir requisições do frontend
 * Trata requisições OPTIONS (preflight) e adiciona headers CORS
 * Prioridade alta para garantir execução antes de outros filtros
 */
@Provider
@Priority(1) // Alta prioridade para garantir execução
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Trata requisições OPTIONS (preflight) - DEVE retornar 200 OK com headers CORS
        String method = requestContext.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            String origin = requestContext.getHeaderString("Origin");
            // Se não houver Origin, permite todas as origens
            if (origin == null || origin.isEmpty()) {
                origin = "*";
            }
            
            Response.ResponseBuilder responseBuilder = Response.ok()
                .header("Access-Control-Allow-Origin", origin)
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, x-requested-with")
                .header("Access-Control-Max-Age", "3600");
            
            // Só adiciona Allow-Credentials se não for "*"
            if (!"*".equals(origin)) {
                responseBuilder.header("Access-Control-Allow-Credentials", "true");
            }
            
            requestContext.abortWith(responseBuilder.build());
            return;
        }
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext, 
                      ContainerResponseContext responseContext) throws IOException {
        // Adiciona headers CORS em todas as respostas (exceto OPTIONS que já foi tratado)
        String method = requestContext.getMethod();
        if (!"OPTIONS".equalsIgnoreCase(method)) {
            String origin = requestContext.getHeaderString("Origin");
            // Se não houver Origin, permite todas as origens
            if (origin == null || origin.isEmpty()) {
                origin = "*";
            }
            
            responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", origin);
            responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
            responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, x-requested-with");
            responseContext.getHeaders().putSingle("Access-Control-Max-Age", "3600");
            
            // Só adiciona Allow-Credentials se não for "*"
            if (!"*".equals(origin)) {
                responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
            }
        }
    }
}

