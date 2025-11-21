package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.Tutor;
import br.com.fiap.model.dao.TutorDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TutorService {
    
    @Inject
    TutorDAO tutorDAO;
    
    @Transactional
    public Tutor criar(Tutor tutor) {
        validarTutor(tutor);
        tutorDAO.criar(tutor);
        return tutor;
    }
    
    public Tutor buscarPorId(Long id) {
        validarId(id);
        Tutor tutor = tutorDAO.buscarPorId(id);
        if (tutor == null) {
            throw new ResourceNotFoundException("Tutor com ID " + id + " não encontrado");
        }
        return tutor;
    }
    
    public List<Tutor> listarTodos() {
        return tutorDAO.listarTodos();
    }
    
    public List<Tutor> listarAtivos() {
        return tutorDAO.listarAtivos();
    }
    
    @Transactional
    public boolean atualizar(Tutor tutor) {
        validarTutor(tutor);
        validarId(tutor.getId());
        
        Tutor tutorExistente = tutorDAO.buscarPorId(tutor.getId());
        if (tutorExistente == null) {
            throw new ResourceNotFoundException("Tutor com ID " + tutor.getId() + " não encontrado");
        }
        
        tutorExistente.setNome(tutor.getNome());
        tutorExistente.setEspecialidade(tutor.getEspecialidade());
        tutorExistente.setEmail(tutor.getEmail());
        tutorExistente.setTelefone(tutor.getTelefone());
        tutorExistente.setAtivo(tutor.getAtivo());
        
        tutorDAO.atualizar(tutorExistente);
        return true;
    }
    
    @Transactional
    public boolean deletar(Long id) {
        validarId(id);
        Tutor tutor = tutorDAO.buscarPorId(id);
        if (tutor == null) {
            throw new ResourceNotFoundException("Tutor com ID " + id + " não encontrado");
        }
        tutorDAO.remover(id);
        return true;
    }
    
    private void validarTutor(Tutor tutor) {
        if (tutor == null) {
            throw new ValidationException("Tutor não pode ser nulo");
        }
        if (tutor.getNome() == null || tutor.getNome().trim().isEmpty()) {
            throw new ValidationException("Nome é obrigatório");
        }
        if (tutor.getEmail() == null || tutor.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email é obrigatório");
        }
    }
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID inválido");
        }
    }
}

