package br.com.fiap.model.dao;

import br.com.fiap.model.entity.PontoRecarga;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PontoRecargaDAO {
    
    @Inject
    EntityManager entityManager;
    
    public void criar(PontoRecarga pontoRecarga) {
        entityManager.persist(pontoRecarga);
        entityManager.flush();
    }
    
    public PontoRecarga buscarPorId(Long id) {
        return entityManager.find(PontoRecarga.class, id);
    }
    
    public List<PontoRecarga> listarTodos() {
        TypedQuery<PontoRecarga> query = entityManager.createQuery(
            "SELECT p FROM PontoRecarga p ORDER BY p.nome", PontoRecarga.class);
        return query.getResultList();
    }
    
    public List<PontoRecarga> buscarPorTipo(PontoRecarga.TipoRecarga tipo) {
        TypedQuery<PontoRecarga> query = entityManager.createQuery(
            "SELECT p FROM PontoRecarga p WHERE p.tipoRecarga = :tipo ORDER BY p.nome", PontoRecarga.class);
        query.setParameter("tipo", tipo);
        return query.getResultList();
    }
    
    public List<PontoRecarga> buscarPorTermo(String termo) {
        TypedQuery<PontoRecarga> query = entityManager.createQuery(
            "SELECT p FROM PontoRecarga p WHERE " +
            "LOWER(p.nome) LIKE LOWER(:termo) OR " +
            "LOWER(p.endereco) LIKE LOWER(:termo) " +
            "ORDER BY p.nome", PontoRecarga.class);
        query.setParameter("termo", "%" + termo + "%");
        return query.getResultList();
    }
    
    public void atualizar(PontoRecarga pontoRecarga) {
        entityManager.merge(pontoRecarga);
        entityManager.flush();
    }
    
    public void remover(Long id) {
        PontoRecarga pontoRecarga = buscarPorId(id);
        if (pontoRecarga != null) {
            entityManager.remove(pontoRecarga);
            entityManager.flush();
        }
    }
}

