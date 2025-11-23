package br.com.fiap.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_ZYNT_AULAS_CURSO")
public class AulaCurso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @NotNull(message = "Curso é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURSO_ID", nullable = false)
    @JsonIgnore
    private Curso curso;
    
    @Transient
    private Long cursoIdTransient;
    
    @NotBlank(message = "Título é obrigatório")
    @Column(name = "TITULO", nullable = false)
    private String titulo;
    
    @Column(name = "DESCRICAO")
    @Lob
    private String descricao;
    
    @Column(name = "URL", length = 500)
    private String url;
    
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
    
    public Curso getCurso() {
        return curso;
    }
    
    public void setCurso(Curso curso) {
        this.curso = curso;
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
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
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
    
    @JsonProperty("cursoId")
    public Long getCursoId() {
        if (cursoIdTransient != null) {
            return cursoIdTransient;
        }
        return curso != null ? curso.getId() : null;
    }
    
    public void setCursoId(Long cursoId) {
        this.cursoIdTransient = cursoId;
    }
}

