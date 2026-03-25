package org.example.analytics.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estabelecimento_id")
    private Long estabelecimentoId;

    private Double valor;

    @Column(name = "tipo_pagamento_id")
    private long tipoPagamentoId;

    @Column(name = "data_transacao")
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(Long estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public long getTipoPagamentoId() {
        return tipoPagamentoId;
    }

    public void setTipoPagamentoId(long tipoPagamentoId) {
        this.tipoPagamentoId = tipoPagamentoId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
