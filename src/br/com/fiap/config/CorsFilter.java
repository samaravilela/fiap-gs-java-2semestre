package br.com.fiap.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Provider
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String DEFAULT_CORS_ORIGINS = "https://fiap-gs-front-2semestre.vercel.app,https://zyntra-he.vercel.app,http://localhost:5173,http://localhost:3000";
    private final Set<String> allowedOrigins;

    public CorsFilter() {
        String origins = System.getenv("CORS_ORIGINS");
        if (origins == null || origins.isEmpty()) {
            origins = DEFAULT_CORS_ORIGINS;
        }
        this.allowedOrigins = new HashSet<>(Arrays.asList(origins.split(",")));
        System.out.println("CORS Filter initialized. Allowed origins: " + allowedOrigins);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String origin = requestContext.getHeaderString("Origin");
        
        // Tratar requisições OPTIONS (preflight)
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            if (origin != null && isOriginAllowed(origin)) {
                Response response = Response.ok()
                        .header("Access-Control-Allow-Origin", origin)
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH")
                        .header("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept, X-Requested-With, Origin")
                        .header("Access-Control-Allow-Credentials", "false")
                        .header("Access-Control-Max-Age", "3600")
                        .build();
                requestContext.abortWith(response);
                return;
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        String origin = requestContext.getHeaderString("Origin");
        
        if (origin != null && isOriginAllowed(origin)) {
            MultivaluedMap<String, Object> headers = responseContext.getHeaders();
            headers.putSingle("Access-Control-Allow-Origin", origin);
            headers.putSingle("Access-Control-Allow-Credentials", "false");
            headers.putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept, X-Requested-With, Origin");
            headers.putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
            headers.putSingle("Access-Control-Max-Age", "3600");
        }
    }

    private boolean isOriginAllowed(String origin) {
        if (origin == null) {
            return false;
        }
        // Verificar origem exata
        if (allowedOrigins.contains(origin)) {
            return true;
        }
        // Verificar se é localhost com qualquer porta
        if (origin.startsWith("http://localhost:") || origin.startsWith("https://localhost:")) {
            return true;
        }
        return false;
    }
}

