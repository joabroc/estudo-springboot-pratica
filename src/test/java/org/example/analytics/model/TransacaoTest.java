package org.example.analytics.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransacaoTest {

    @Test
    void gettersESettersDevemManipularTodosOsCampos() {
        Transacao transacao = new Transacao();

        transacao.setId(1L);
        transacao.setEstabelecimentoId(200L);
        transacao.setValor(123.45);
        transacao.setTipoPagamentoId(3L);
        transacao.setData("2026-03-31");

        assertThat(transacao.getId()).isEqualTo(1L);
        assertThat(transacao.getEstabelecimentoId()).isEqualTo(200L);
        assertThat(transacao.getValor()).isEqualTo(123.45);
        assertThat(transacao.getTipoPagamentoId()).isEqualTo(3L);
        assertThat(transacao.getData()).isEqualTo("2026-03-31");
    }
}

