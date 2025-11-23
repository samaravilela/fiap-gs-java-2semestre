package br.com.fiap.model.dao;

import br.com.fiap.model.entity.OficinaServico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OficinaServicoDAO {
    
    @Inject
    EntityManager entityManager;
    
    public void criar(OficinaServico oficinaServico) {
        entityManager.persist(oficinaServico);
        entityManager.flush();
    }
    
    public OficinaServico buscarPorId(Long id) {
        return entityManager.find(OficinaServico.class, id);
    }
    
    public List<OficinaServico> listarTodos() {
        TypedQuery<OficinaServico> query = entityManager.createQuery(
            "SELECT os FROM OficinaServico os ORDER BY os.nome", OficinaServico.class);
        return query.getResultList();
    }
    
    public List<OficinaServico> buscarPorOficinaId(Long oficinaId) {
        TypedQuery<OficinaServico> query = entityManager.createQuery(
            "SELECT os FROM OficinaServico os WHERE os.oficina.id = :oficinaId ORDER BY os.nome", OficinaServico.class);
        query.setParameter("oficinaId", oficinaId);
        return query.getResultList();
    }
    
    public List<OficinaServico> listarAtivos() {
        TypedQuery<OficinaServico> query = entityManager.createQuery(
            "SELECT os FROM OficinaServico os WHERE os.ativo = 'S' ORDER BY os.nome", OficinaServico.class);
        return query.getResultList();
    }
    
    public void atualizar(OficinaServico oficinaServico) {
        entityManager.merge(oficinaServico);
        entityManager.flush();
    }
    
    public void remover(Long id) {
        OficinaServico oficinaServico = buscarPorId(id);
        if (oficinaServico != null) {
            entityManager.remove(oficinaServico);
            entityManager.flush();
        }
    }
}

