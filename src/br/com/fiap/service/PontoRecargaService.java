package br.com.fiap.service;

import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.PontoRecarga;
import br.com.fiap.model.dao.PontoRecargaDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PontoRecargaService {
    
    @Inject
    PontoRecargaDAO pontoRecargaDAO;
    
    @Transactional
    public PontoRecarga criar(PontoRecarga pontoRecarga) {
        validarPontoRecarga(pontoRecarga);
        pontoRecargaDAO.criar(pontoRecarga);
        return pontoRecarga;
    }
    
    public PontoRecarga buscarPorId(Long id) {
        validarId(id);
        PontoRecarga pontoRecarga = pontoRecargaDAO.buscarPorId(id);
        if (pontoRecarga == null) {
            throw new ResourceNotFoundException("Ponto de recarga com ID " + id + " não encontrado");
        }
        return pontoRecarga;
    }
    
    public List<PontoRecarga> listarTodos() {
        return pontoRecargaDAO.listarTodos();
    }
    
    public List<PontoRecarga> buscarPorTipo(PontoRecarga.TipoRecarga tipo) {
        if (tipo == null) {
            throw new ValidationException("Tipo de recarga é obrigatório");
        }
        return pontoRecargaDAO.buscarPorTipo(tipo);
    }
    
    public List<PontoRecarga> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }
        return pontoRecargaDAO.buscarPorTermo(termo);
    }
    
    @Transactional
    public boolean atualizar(PontoRecarga pontoRecarga) {
        validarPontoRecarga(pontoRecarga);
        validarId(pontoRecarga.getId());
        
        PontoRecarga pontoExistente = pontoRecargaDAO.buscarPorId(pontoRecarga.getId());
        if (pontoExistente == null) {
            throw new ResourceNotFoundException("Ponto de recarga com ID " + pontoRecarga.getId() + " não encontrado");
        }
        
        pontoExistente.setNome(pontoRecarga.getNome());
        pontoExistente.setEndereco(pontoRecarga.getEndereco());
        pontoExistente.setTipoRecarga(pontoRecarga.getTipoRecarga());
        
        pontoRecargaDAO.atualizar(pontoExistente);
        return true;
    }
    
    @Transactional
    public boolean deletar(Long id) {
        validarId(id);
        PontoRecarga pontoRecarga = pontoRecargaDAO.buscarPorId(id);
        if (pontoRecarga == null) {
            throw new ResourceNotFoundException("Ponto de recarga com ID " + id + " não encontrado");
        }
        pontoRecargaDAO.remover(id);
        return true;
    }
    
    private void validarPontoRecarga(PontoRecarga pontoRecarga) {
        if (pontoRecarga == null) {
            throw new ValidationException("Ponto de recarga não pode ser nulo");
        }
        if (pontoRecarga.getNome() == null || pontoRecarga.getNome().trim().isEmpty()) {
            throw new ValidationException("Nome é obrigatório");
        }
        if (pontoRecarga.getEndereco() == null || pontoRecarga.getEndereco().trim().isEmpty()) {
            throw new ValidationException("Endereço é obrigatório");
        }
    }
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID inválido");
        }
    }
}

