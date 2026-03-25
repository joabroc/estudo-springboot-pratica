package org.example.analytics.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "v_visao_geral")
public class TransacaoCompleta {

    @Id
    @Column(name = "transacao_id")
    private Long id;

    private String estabelecimento;

    private String categoria;

    private Double valor;

    @Column(name = "forma_pagamento")
    private String formaPagamento;

    @Column(name = "data_transacao")
    private LocalDateTime data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
