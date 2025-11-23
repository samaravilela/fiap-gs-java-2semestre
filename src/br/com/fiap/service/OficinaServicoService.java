package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.OficinaServico;
import br.com.fiap.model.entity.Oficina;
import br.com.fiap.model.dao.OficinaServicoDAO;
import br.com.fiap.model.dao.OficinaDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class OficinaServicoService {
    
    @Inject
    OficinaServicoDAO oficinaServicoDAO;
    
    @Inject
    OficinaDAO oficinaDAO;
    
    @Transactional
    public OficinaServico criar(OficinaServico oficinaServico) {
        validarOficinaServico(oficinaServico);
        
        // Verificar se a oficina existe
        if (oficinaServico.getOficina() != null && oficinaServico.getOficina().getId() != null) {
            Oficina oficina = oficinaDAO.buscarPorId(oficinaServico.getOficina().getId());
            if (oficina == null) {
                throw new ResourceNotFoundException("Oficina com ID " + oficinaServico.getOficina().getId() + " não encontrada");
            }
            oficinaServico.setOficina(oficina);
        }
        
        oficinaServicoDAO.criar(oficinaServico);
        return oficinaServico;
    }
    
    public OficinaServico buscarPorId(Long id) {
        validarId(id);
        OficinaServico oficinaServico = oficinaServicoDAO.buscarPorId(id);
        if (oficinaServico == null) {
            throw new ResourceNotFoundException("Serviço com ID " + id + " não encontrado");
        }
        return oficinaServico;
    }
    
    public List<OficinaServico> listarTodos() {
        return oficinaServicoDAO.listarTodos();
    }
    
    public List<OficinaServico> buscarPorOficinaId(Long oficinaId) {
        validarId(oficinaId);
        return oficinaServicoDAO.buscarPorOficinaId(oficinaId);
    }
    
    public List<OficinaServico> listarAtivos() {
        return oficinaServicoDAO.listarAtivos();
    }
    
    @Transactional
    public boolean atualizar(OficinaServico oficinaServico) {
        validarOficinaServico(oficinaServico);
        validarId(oficinaServico.getId());
        
        OficinaServico servicoExistente = oficinaServicoDAO.buscarPorId(oficinaServico.getId());
        if (servicoExistente == null) {
            throw new ResourceNotFoundException("Serviço com ID " + oficinaServico.getId() + " não encontrado");
        }
        
        servicoExistente.setNome(oficinaServico.getNome());
        servicoExistente.setDescricao(oficinaServico.getDescricao());
        servicoExistente.setAtivo(oficinaServico.getAtivo());
        
        oficinaServicoDAO.atualizar(servicoExistente);
        return true;
    }
    
    @Transactional
    public boolean deletar(Long id) {
        validarId(id);
        OficinaServico oficinaServico = oficinaServicoDAO.buscarPorId(id);
        if (oficinaServico == null) {
            throw new ResourceNotFoundException("Serviço com ID " + id + " não encontrado");
        }
        oficinaServicoDAO.remover(id);
        return true;
    }
    
    private void validarOficinaServico(OficinaServico oficinaServico) {
        if (oficinaServico == null) {
            throw new ValidationException("Serviço não pode ser nulo");
        }
        if (oficinaServico.getNome() == null || oficinaServico.getNome().trim().isEmpty()) {
            throw new ValidationException("Nome do serviço é obrigatório");
        }
        if (oficinaServico.getOficina() == null || oficinaServico.getOficina().getId() == null) {
            throw new ValidationException("Oficina é obrigatória");
        }
    }
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID inválido");
        }
    }
}

