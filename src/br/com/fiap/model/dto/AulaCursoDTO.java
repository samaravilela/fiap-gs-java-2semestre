package br.com.fiap.model.dto;

import java.time.LocalDateTime;

/**
 * DTO simples para retornar dados de AulaCurso sem validações JPA
 */
public class AulaCursoDTO {
    private Long id;
    private Long cursoId;
    private String titulo;
    private String descricao;
    private String url;
    private String ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    public AulaCursoDTO() {
    }
    
    public AulaCursoDTO(Long id, Long cursoId, String titulo, String descricao, String url, String ativo, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCursoId() {
        return cursoId;
    }
    
    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
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
}

