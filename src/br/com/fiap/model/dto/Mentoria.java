package br.com.fiap.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe modelo para Mentoria
 * Representa uma mentoria agendada
 */
public class Mentoria {
    private Long id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate data;
    private LocalDateTime dataCriacao;
    private String status; // AGENDADA, CONFIRMADA, CANCELADA, REALIZADA

    // Construtores
    public Mentoria() {}

    public Mentoria(Long id, String nomeCompleto, String cpf, String email, 
                   String telefone, LocalDate data, LocalDateTime dataCriacao, String status) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.data = data;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Mentoria{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", data=" + data +
                ", status='" + status + '\'' +
                '}';
    }
}

