package br.com.fiap.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "T_ZYNT_OFICINAS")
public class Oficina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @NotBlank(message = "Nome do empreendimento é obrigatório")
    @Column(name = "NOME_EMPREENDIMENTO", nullable = false)
    private String nomeEmpreendimento;
    
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos")
    @Column(name = "CNPJ", nullable = false, unique = true, length = 14)
    private String cnpj;
    
    @NotBlank(message = "Nome da empresa é obrigatório")
    @Column(name = "NOME_EMPRESA", nullable = false)
    private String nomeEmpresa;
    
    @NotBlank(message = "Localização é obrigatória")
    @Size(min = 10, message = "Localização deve ter no mínimo 10 caracteres")
    @Column(name = "LOCALIZACAO", nullable = false)
    @Lob
    private String localizacao;
    
    @OneToMany(mappedBy = "oficina", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OficinaServico> servicos;
    
    @Column(name = "CIDADE")
    private String cidade;
    
    @Column(name = "ESTADO", length = 2)
    private String estado;
    
    @Column(name = "ESPECIALIDADE")
    private String especialidade;
    
    @Column(name = "AVALIACAO")
    private Double avaliacao;
    
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
    
    @Column(name = "DATA_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;
    
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusOficina status;
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (status == null) {
            status = StatusOficina.PENDENTE;
        }
        if (avaliacao == null) {
            avaliacao = 0.0;
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
    
    public List<OficinaServico> getServicos() {
        return servicos;
    }
    
    public void setServicos(List<OficinaServico> servicos) {
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
    
    public StatusOficina getStatus() {
        return status;
    }
    
    public void setStatus(StatusOficina status) {
        this.status = status;
    }
    
    public enum StatusOficina {
        PENDENTE, APROVADA, REJEITADA, INATIVA
    }
}

