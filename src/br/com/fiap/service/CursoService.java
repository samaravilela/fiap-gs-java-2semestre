package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.Curso;
import br.com.fiap.model.dao.CursoDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CursoService {
    
    @Inject
    CursoDAO cursoDAO;
    
    @Transactional
    public Curso criar(Curso curso) {
        validarCurso(curso);
        cursoDAO.criar(curso);
        return curso;
    }
    
    public Curso buscarPorId(Long id) {
        validarId(id);
        Curso curso = cursoDAO.buscarPorId(id);
        if (curso == null) {
            throw new ResourceNotFoundException("Curso com ID " + id + " não encontrado");
        }
        return curso;
    }
    
    public List<Curso> listarTodos() {
        return cursoDAO.listarTodos();
    }
    
    public List<Curso> listarAtivos() {
        return cursoDAO.listarAtivos();
    }
    
    @Transactional
    public boolean atualizar(Curso curso) {
        validarCurso(curso);
        validarId(curso.getId());
        
        Curso cursoExistente = cursoDAO.buscarPorId(curso.getId());
        if (cursoExistente == null) {
            throw new ResourceNotFoundException("Curso com ID " + curso.getId() + " não encontrado");
        }
        
        cursoExistente.setTitulo(curso.getTitulo());
        cursoExistente.setDescricao(curso.getDescricao());
        cursoExistente.setAtivo(curso.getAtivo());
        
        cursoDAO.atualizar(cursoExistente);
        return true;
    }
    
    @Transactional
    public boolean deletar(Long id) {
        validarId(id);
        Curso curso = cursoDAO.buscarPorId(id);
        if (curso == null) {
            throw new ResourceNotFoundException("Curso com ID " + id + " não encontrado");
        }
        cursoDAO.remover(id);
        return true;
    }
    
    private void validarCurso(Curso curso) {
        if (curso == null) {
            throw new ValidationException("Curso não pode ser nulo");
        }
        if (curso.getTitulo() == null || curso.getTitulo().trim().isEmpty()) {
            throw new ValidationException("Título é obrigatório");
        }
    }
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID inválido");
        }
    }
}

