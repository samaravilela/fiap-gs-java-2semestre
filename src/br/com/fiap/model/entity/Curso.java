package br.com.fiap.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "T_ZYNT_CURSOS")
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @NotBlank(message = "Título é obrigatório")
    @Column(name = "TITULO", nullable = false)
    private String titulo;
    
    @Column(name = "DESCRICAO")
    @Lob
    private String descricao;
    
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
    
    @Column(name = "DATA_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;
    
    @Column(name = "ATIVO")
    private String ativo;
    
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AulaCurso> aulas;
    
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
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
    
    public List<AulaCurso> getAulas() {
        return aulas;
    }
    
    public void setAulas(List<AulaCurso> aulas) {
        this.aulas = aulas;
    }
}

