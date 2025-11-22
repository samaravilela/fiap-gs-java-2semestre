package br.com.fiap.service;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.Mentoria;
import br.com.fiap.model.entity.Usuario;
import br.com.fiap.model.dao.MentoriaDAO;
import br.com.fiap.model.dao.UsuarioDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MentoriaService {
    
    @Inject
    MentoriaDAO mentoriaDAO;
    
    @Inject
    UsuarioDAO usuarioDAO;
    
    @Transactional
    public Mentoria criar(Mentoria mentoria) {
        validarMentoria(mentoria);
        
        // Verificar se o email está cadastrado no sistema
        Usuario usuario = usuarioDAO.buscarPorEmail(mentoria.getEmail());
        if (usuario == null) {
            throw new BusinessRuleException("Email não cadastrado. É necessário ter uma conta para agendar uma mentoria. Por favor, cadastre-se primeiro.");
        }
        
        // Associar o usuário encontrado à mentoria
        mentoria.setUsuario(usuario);
        
        if (mentoria.getData().isBefore(LocalDate.now())) {
            throw new BusinessRuleException("Data da mentoria não pode ser no passado");
        }
        
        List<Mentoria> mentoriasNoDia = mentoriaDAO.buscarPorData(mentoria.getData());
        if (mentoriasNoDia.size() >= 10) {
            throw new BusinessRuleException("Não há horários disponíveis para esta data");
        }
        
        mentoriaDAO.criar(mentoria);
        return mentoria;
    }
    
    public Mentoria buscarPorId(Long id) {
        validarId(id);
        Mentoria mentoria = mentoriaDAO.buscarPorId(id);
        if (mentoria == null) {
            throw new ResourceNotFoundException("Mentoria com ID " + id + " não encontrada");
        }
        return mentoria;
    }
    
    public List<Mentoria> listarTodos() {
        return mentoriaDAO.listarTodos();
    }
    
    public List<Mentoria> buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email é obrigatório");
        }
        return mentoriaDAO.buscarPorEmail(email.trim());
    }
    
    @Transactional
    public boolean atualizar(Mentoria mentoria) {
        validarMentoria(mentoria);
        validarId(mentoria.getId());
        
        Mentoria mentoriaExistente = mentoriaDAO.buscarPorId(mentoria.getId());
        if (mentoriaExistente == null) {
            throw new ResourceNotFoundException("Mentoria com ID " + mentoria.getId() + " não encontrada");
        }
        
        // Verificar se o email está cadastrado no sistema (se foi alterado)
        if (!mentoriaExistente.getEmail().equals(mentoria.getEmail())) {
            Usuario usuario = usuarioDAO.buscarPorEmail(mentoria.getEmail());
            if (usuario == null) {
                throw new BusinessRuleException("Email não cadastrado. É necessário ter uma conta para agendar uma mentoria. Por favor, cadastre-se primeiro.");
            }
            mentoriaExistente.setUsuario(usuario);
        }
        
        if (mentoria.getData().isBefore(LocalDate.now())) {
            throw new BusinessRuleException("Data da mentoria não pode ser no passado");
        }
        
        mentoriaExistente.setNomeCompleto(mentoria.getNomeCompleto());
        mentoriaExistente.setCpf(mentoria.getCpf());
        mentoriaExistente.setEmail(mentoria.getEmail());
        mentoriaExistente.setTelefone(mentoria.getTelefone());
        mentoriaExistente.setData(mentoria.getData());
        
        mentoriaDAO.atualizar(mentoriaExistente);
        return true;
    }
    
    @Transactional
    public boolean cancelar(Long id) {
        validarId(id);
        Mentoria mentoria = mentoriaDAO.buscarPorId(id);
        if (mentoria == null) {
            throw new ResourceNotFoundException("Mentoria com ID " + id + " não encontrada");
        }
        mentoria.setStatus(Mentoria.StatusMentoria.CANCELADA);
        mentoriaDAO.atualizar(mentoria);
        return true;
    }
    
    @Transactional
    public boolean deletar(Long id) {
        validarId(id);
        Mentoria mentoria = mentoriaDAO.buscarPorId(id);
        if (mentoria == null) {
            throw new ResourceNotFoundException("Mentoria com ID " + id + " não encontrada");
        }
        mentoriaDAO.remover(id);
        return true;
    }
    
    private void validarMentoria(Mentoria mentoria) {
        if (mentoria == null) {
            throw new ValidationException("Mentoria não pode ser nula");
        }
        if (mentoria.getNomeCompleto() == null || mentoria.getNomeCompleto().trim().isEmpty()) {
            throw new ValidationException("Nome completo é obrigatório");
        }
        if (mentoria.getCpf() == null || !mentoria.getCpf().matches("\\d{11}")) {
            throw new ValidationException("CPF deve conter 11 dígitos");
        }
        if (mentoria.getEmail() == null || mentoria.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email é obrigatório");
        }
        if (mentoria.getData() == null) {
            throw new ValidationException("Data é obrigatória");
        }
    }
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID inválido");
        }
    }
}

