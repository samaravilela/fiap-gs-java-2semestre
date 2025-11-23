package br.com.fiap.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_ZYNT_OFICINA_SERVICOS")
public class OficinaServico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OFICINA_ID", nullable = false)
    @JsonIgnore
    private Oficina oficina;
    
    @NotBlank(message = "Nome do serviço é obrigatório")
    @Column(name = "NOME", nullable = false)
    private String nome;
    
    @Column(name = "DESCRICAO")
    @Lob
    private String descricao;
    
    @Column(name = "ATIVO")
    private String ativo;
    
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
    
    @Column(name = "DATA_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (ativo == null) {
            ativo = "S";
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Oficina getOficina() {
        return oficina;
    }
    
    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getAtivo() {
        return ativo;
    }
    
    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
    
    public Boolean isAtivo() {
        return "S".equals(ativo);
    }
    
    public void setAtivoBoolean(Boolean ativo) {
        this.ativo = ativo ? "S" : "N";
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    // Método auxiliar para obter o ID da oficina (evita lazy loading)
    public Long getOficinaId() {
        return oficina != null ? oficina.getId() : null;
    }
}

