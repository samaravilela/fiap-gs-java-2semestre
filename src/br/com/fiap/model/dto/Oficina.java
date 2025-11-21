package br.com.fiap.model.dto;

import java.time.LocalDateTime;

/**
 * Classe modelo para Oficina
 * Representa uma oficina cadastrada
 */
public class Oficina {
    private Long id;
    private String nomeEmpreendimento;
    private String cnpj;
    private String nomeEmpresa;
    private String localizacao;
    private String servicos;
    private String cidade;
    private String estado;
    private String especialidade;
    private Double avaliacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String status; // PENDENTE, APROVADA, REJEITADA, INATIVA

    // Construtores
    public Oficina() {}

    public Oficina(Long id, String nomeEmpreendimento, String cnpj, String nomeEmpresa,
                  String localizacao, String servicos, String cidade, String estado,
                  String especialidade, Double avaliacao, LocalDateTime dataCriacao,
                  LocalDateTime dataAtualizacao, String status) {
        this.id = id;
        this.nomeEmpreendimento = nomeEmpreendimento;
        this.cnpj = cnpj;
        this.nomeEmpresa = nomeEmpresa;
        this.localizacao = localizacao;
        this.servicos = servicos;
        this.cidade = cidade;
        this.estado = estado;
        this.especialidade = especialidade;
        this.avaliacao = avaliacao;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpreendimento() {
        return nomeEmpreendimento;
    }

    public void setNomeEmpreendimento(String nomeEmpreendimento) {
        this.nomeEmpreendimento = nomeEmpreendimento;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getServicos() {
        return servicos;
    }

    public void setServicos(String servicos) {
        this.servicos = servicos;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Oficina{" +
                "id=" + id +
                ", nomeEmpreendimento='" + nomeEmpreendimento + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nomeEmpresa='" + nomeEmpresa + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

