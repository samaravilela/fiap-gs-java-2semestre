package br.com.fiap.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_ZYNT_PONTOS_RECARGA")
public class PontoRecarga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "NOME", nullable = false)
    private String nome;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Column(name = "ENDERECO", nullable = false, length = 500)
    private String endereco;
    
    @Column(name = "TIPO_RECARGA", length = 50)
    @Enumerated(EnumType.STRING)
    private TipoRecarga tipoRecarga;
    
    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;
    
    @Column(name = "DATA_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (tipoRecarga == null) {
            tipoRecarga = TipoRecarga.AC;
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
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public TipoRecarga getTipoRecarga() {
        return tipoRecarga;
    }
    
    public void setTipoRecarga(TipoRecarga tipoRecarga) {
        this.tipoRecarga = tipoRecarga;
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
    
    // Enum
    public enum TipoRecarga {
        AC, DC, AC_DC
    }
}
