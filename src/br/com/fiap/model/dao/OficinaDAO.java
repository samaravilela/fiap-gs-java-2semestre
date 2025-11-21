package br.com.fiap.model.dao;

import br.com.fiap.model.entity.Oficina;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OficinaDAO {
    
    @Inject
    EntityManager entityManager;
    
    public void criar(Oficina oficina) {
        entityManager.persist(oficina);
        entityManager.flush();
    }
    
    public Oficina buscarPorId(Long id) {
        return entityManager.find(Oficina.class, id);
    }
    
    public Oficina buscarPorCnpj(String cnpj) {
        try {
            TypedQuery<Oficina> query = entityManager.createQuery(
                "SELECT o FROM Oficina o WHERE o.cnpj = :cnpj", Oficina.class);
            query.setParameter("cnpj", cnpj);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Oficina> listarTodos() {
        TypedQuery<Oficina> query = entityManager.createQuery(
            "SELECT o FROM Oficina o ORDER BY o.nomeEmpreendimento", Oficina.class);
        return query.getResultList();
    }
    
    public List<Oficina> buscarPorCidade(String cidade) {
        TypedQuery<Oficina> query = entityManager.createQuery(
            "SELECT o FROM Oficina o WHERE o.cidade = :cidade ORDER BY o.nomeEmpreendimento", Oficina.class);
        query.setParameter("cidade", cidade);
        return query.getResultList();
    }
    
    public List<Oficina> buscarPorEstado(String estado) {
        TypedQuery<Oficina> query = entityManager.createQuery(
            "SELECT o FROM Oficina o WHERE o.estado = :estado ORDER BY o.nomeEmpreendimento", Oficina.class);
        query.setParameter("estado", estado);
        return query.getResultList();
    }
    
    public List<Oficina> buscarPorEspecialidade(String especialidade) {
        TypedQuery<Oficina> query = entityManager.createQuery(
            "SELECT o FROM Oficina o WHERE o.especialidade = :especialidade ORDER BY o.nomeEmpreendimento", Oficina.class);
        query.setParameter("especialidade", especialidade);
        return query.getResultList();
    }
    
    public List<Oficina> buscarPorTermo(String termo) {
        TypedQuery<Oficina> query = entityManager.createQuery(
            "SELECT o FROM Oficina o WHERE " +
            "LOWER(o.nomeEmpreendimento) LIKE LOWER(:termo) OR " +
            "LOWER(o.cidade) LIKE LOWER(:termo) OR " +
            "LOWER(o.estado) LIKE LOWER(:termo) OR " +
            "LOWER(o.especialidade) LIKE LOWER(:termo) " +
            "ORDER BY o.nomeEmpreendimento", Oficina.class);
        query.setParameter("termo", "%" + termo + "%");
        return query.getResultList();
    }
    
    public void atualizar(Oficina oficina) {
        entityManager.merge(oficina);
        entityManager.flush();
    }
    
    public void remover(Long id) {
        Oficina oficina = buscarPorId(id);
        if (oficina != null) {
            entityManager.remove(oficina);
            entityManager.flush();
        }
    }
    
    public boolean cnpjExiste(String cnpj) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(o) FROM Oficina o WHERE o.cnpj = :cnpj", Long.class);
        query.setParameter("cnpj", cnpj);
        return query.getSingleResult() > 0;
    }
}

