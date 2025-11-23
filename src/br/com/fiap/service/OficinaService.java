package br.com.fiap.service;

import br.com.fiap.exception.BusinessRuleException;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.exception.ValidationException;
import br.com.fiap.model.entity.Oficina;
import br.com.fiap.model.dao.OficinaDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class OficinaService {
    
    @Inject
    OficinaDAO oficinaDAO;
    
    @Transactional
    public Oficina criar(Oficina oficina) {
        validarOficina(oficina);
        
        if (oficinaDAO.cnpjExiste(oficina.getCnpj())) {
            throw new BusinessRuleException("CNPJ já cadastrado no sistema");
        }
        
        if (oficina.getCnpj().length() != 14) {
            throw new ValidationException("CNPJ deve conter 14 dígitos");
        }
        
        extrairCidadeEstado(oficina);
        oficinaDAO.criar(oficina);
        return oficina;
    }
    
    public Oficina buscarPorId(Long id) {
        validarId(id);
        Oficina oficina = oficinaDAO.buscarPorId(id);
        if (oficina == null) {
            throw new ResourceNotFoundException("Oficina com ID " + id + " não encontrada");
        }
        return oficina;
    }
    
    public List<Oficina> listarTodos() {
        return oficinaDAO.listarTodos();
    }
    
    public List<Oficina> buscarPorTermo(String termo) {
        return oficinaDAO.buscarPorTermo(termo);
    }
    
    @Transactional
    public boolean atualizar(Oficina oficina) {
        validarOficina(oficina);
        validarId(oficina.getId());
        
        Oficina oficinaExistente = oficinaDAO.buscarPorId(oficina.getId());
        if (oficinaExistente == null) {
            throw new ResourceNotFoundException("Oficina com ID " + oficina.getId() + " não encontrada");
        }
        
        if (!oficina.getCnpj().equals(oficinaExistente.getCnpj()) && 
            oficinaDAO.cnpjExiste(oficina.getCnpj())) {
            throw new BusinessRuleException("CNPJ já cadastrado no sistema");
        }
        
        oficinaExistente.setNomeEmpreendimento(oficina.getNomeEmpreendimento());
        oficinaExistente.setCnpj(oficina.getCnpj());
        oficinaExistente.setNomeEmpresa(oficina.getNomeEmpresa());
        oficinaExistente.setLocalizacao(oficina.getLocalizacao());
        oficinaExistente.setEspecialidade(oficina.getEspecialidade());
        
        extrairCidadeEstado(oficinaExistente);
        oficinaDAO.atualizar(oficinaExistente);
        return true;
    }
    
    @Transactional
    public boolean aprovar(Long id) {
        validarId(id);
        Oficina oficina = oficinaDAO.buscarPorId(id);
        if (oficina == null) {
            throw new ResourceNotFoundException("Oficina com ID " + id + " não encontrada");
        }
        oficina.setStatus(Oficina.StatusOficina.APROVADA);
        oficinaDAO.atualizar(oficina);
        return true;
    }
    
    @Transactional
    public boolean deletar(Long id) {
        validarId(id);
        Oficina oficina = oficinaDAO.buscarPorId(id);
        if (oficina == null) {
            throw new ResourceNotFoundException("Oficina com ID " + id + " não encontrada");
        }
        oficinaDAO.remover(id);
        return true;
    }
    
    private void validarOficina(Oficina oficina) {
        if (oficina == null) {
            throw new ValidationException("Oficina não pode ser nula");
        }
        if (oficina.getNomeEmpreendimento() == null || oficina.getNomeEmpreendimento().trim().isEmpty()) {
            throw new ValidationException("Nome do empreendimento é obrigatório");
        }
        if (oficina.getCnpj() == null || oficina.getCnpj().trim().isEmpty()) {
            throw new ValidationException("CNPJ é obrigatório");
        }
        if (oficina.getLocalizacao() == null || oficina.getLocalizacao().trim().isEmpty()) {
            throw new ValidationException("Localização é obrigatória");
        }
    }
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("ID inválido");
        }
    }
    
    private void extrairCidadeEstado(Oficina oficina) {
        String localizacao = oficina.getLocalizacao();
        if (localizacao != null && !localizacao.trim().isEmpty()) {
            String[] partes = localizacao.split(",");
            if (partes.length > 0) {
                String ultimaParte = partes[partes.length - 1].trim();
                if (ultimaParte.length() == 2) {
                    oficina.setEstado(ultimaParte.toUpperCase());
                }
                if (partes.length > 1) {
                    oficina.setCidade(partes[partes.length - 2].trim());
                }
            }
        }
    }
}

