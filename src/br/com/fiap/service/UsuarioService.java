package br.com.fiap.service;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.Usuario;
import br.com.fiap.model.dao.UsuarioDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Service para regras de negócio da entidade Usuario
 */
@ApplicationScoped
public class UsuarioService {
    
    @Inject
    UsuarioDAO usuarioDAO;
    
    /**
     * Cria um novo usuário com validações
     */
    @Transactional
    public Usuario criar(Usuario usuario) {
        validarUsuario(usuario);
        
        if (usuarioDAO.emailExiste(usuario.getEmail())) {
            throw new BusinessRuleException("Email já cadastrado no sistema");
        }
        
        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        usuarioDAO.criar(usuario);
        return usuario;
    }
    
    /**
     * Autentica um usuário
     */
    public Usuario autenticar(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email é obrigatório");
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            throw new ValidationException("Senha é obrigatória");
        }
        
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        
        if (usuario == null) {
            throw new ResourceNotFoundException("Email ou senha inválidos");
        }
        
        String senhaCriptografada = criptografarSenha(senha);
        if (!usuario.getSenha().equals(senhaCriptografada)) {
            throw new ResourceNotFoundException("Email ou senha inválidos");
        }
        
        return usuario;
    }
    
    /**
     * Lista todos os usuários
     */
    public java.util.List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }
    
    /**
     * Busca usuário por ID
     */
    public Usuario buscarPorId(Long id) {
        validarId(id);
        Usuario usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        return usuario;
    }
    
    /**
     * Atualiza um usuário existente
     */
    @Transactional
    public boolean atualizar(Usuario usuario) {
        validarUsuario(usuario);
        validarId(usuario.getId());
        
        Usuario usuarioExistente = usuarioDAO.buscarPorId(usuario.getId());
        if (usuarioExistente == null) {
            throw new ResourceNotFoundException("Usuário com ID " + usuario.getId() + " não encontrado");
        }
        
        if (!usuario.getEmail().equals(usuarioExistente.getEmail()) && 
            usuarioDAO.emailExiste(usuario.getEmail())) {
            throw new BusinessRuleException("Email já cadastrado no sistema");
        }
        
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        
        if (usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty()) {
            usuarioExistente.setSenha(criptografarSenha(usuario.getSenha()));
        }
        
        usuarioDAO.atualizar(usuarioExistente);
        return true;
    }
    
    /**
     * Deleta um usuário
     */
    @Transactional
    public boolean deletar(Long id) {
        validarId(id);
        
        Usuario usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        
        usuarioDAO.remover(id);
        return true;
    }
    
    /**
     * Valida os dados do usuário
     */
    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new ValidationException("Usuário não pode ser nulo");
        }
        
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new ValidationException("Nome é obrigatório");
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email é obrigatório");
        }
        
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Email inválido");
        }
        
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new ValidationException("Senha é obrigatória");
        }
        
        if (usuario.getSenha().length() < 6) {
            throw new ValidationException("Senha deve ter no mínimo 6 caracteres");
        }
    }
    
    /**
     * Valida o ID do usuário
     */
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID do usuário inválido");
        }
    }
    
    /**
     * Criptografa senha usando SHA-256
     */
    private String criptografarSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }
}

