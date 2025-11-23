package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.AulaCurso;
import br.com.fiap.model.entity.Curso;
import br.com.fiap.model.dao.AulaCursoDAO;
import br.com.fiap.model.dao.CursoDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AulaCursoService {
    
    @Inject
    AulaCursoDAO aulaCursoDAO;
    
    @Inject
    CursoDAO cursoDAO;
    
    @Transactional
    public AulaCurso criar(AulaCurso aulaCurso) {
        validarAulaCurso(aulaCurso);
        
        // Verificar se o curso existe
        if (aulaCurso.getCurso() != null && aulaCurso.getCurso().getId() != null) {
            Curso curso = cursoDAO.buscarPorId(aulaCurso.getCurso().getId());
            if (curso == null) {
                throw new ResourceNotFoundException("Curso com ID " + aulaCurso.getCurso().getId() + " não encontrado");
            }
            aulaCurso.setCurso(curso);
        }
        
        aulaCursoDAO.criar(aulaCurso);
        return aulaCurso;
    }
    
    @Transactional
    public AulaCurso buscarPorId(Long id) {
        validarId(id);
        AulaCurso aulaCurso = aulaCursoDAO.buscarPorId(id);
        if (aulaCurso == null) {
            throw new ResourceNotFoundException("Aula com ID " + id + " não encontrada");
        }
        // Forçar o carregamento do relacionamento
        if (aulaCurso.getCurso() != null) {
            aulaCurso.getCurso().getId();
        }
        return aulaCurso;
    }
    
    @Transactional
    public List<AulaCurso> listarTodos() {
        return aulaCursoDAO.listarTodos();
    }
    
    @Transactional
    public List<AulaCurso> buscarPorCursoId(Long cursoId) {
        validarId(cursoId);
        return aulaCursoDAO.buscarPorCursoId(cursoId);
    }
    
    @Transactional
    public List<AulaCurso> listarAtivas() {
        return aulaCursoDAO.listarAtivas();
    }
    
    @Transactional
    public boolean atualizar(AulaCurso aulaCurso) {
        validarAulaCurso(aulaCurso);
        validarId(aulaCurso.getId());
        
        AulaCurso aulaExistente = aulaCursoDAO.buscarPorId(aulaCurso.getId());
        if (aulaExistente == null) {
            throw new ResourceNotFoundException("Aula com ID " + aulaCurso.getId() + " não encontrada");
        }
        
        aulaExistente.setTitulo(aulaCurso.getTitulo());
        aulaExistente.setDescricao(aulaCurso.getDescricao());
        aulaExistente.setUrl(aulaCurso.getUrl());
        aulaExistente.setAtivo(aulaCurso.getAtivo());
        
        aulaCursoDAO.atualizar(aulaExistente);
        return true;
    }
    
    @Transactional
    public boolean deletar(Long id) {
        validarId(id);
        AulaCurso aulaCurso = aulaCursoDAO.buscarPorId(id);
        if (aulaCurso == null) {
            throw new ResourceNotFoundException("Aula com ID " + id + " não encontrada");
        }
        aulaCursoDAO.remover(id);
        return true;
    }
    
    private void validarAulaCurso(AulaCurso aulaCurso) {
        if (aulaCurso == null) {
            throw new ValidationException("Aula não pode ser nula");
        }
        if (aulaCurso.getTitulo() == null || aulaCurso.getTitulo().trim().isEmpty()) {
            throw new ValidationException("Título é obrigatório");
        }
        if (aulaCurso.getCurso() == null || aulaCurso.getCurso().getId() == null) {
            throw new ValidationException("Curso é obrigatório");
        }
    }
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID inválido");
        }
    }
}

