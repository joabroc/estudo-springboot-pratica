package org.example.analytics.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TipoPagamentoTest {

    @Test
    void gettersESettersDevemManipularTodosOsCampos() {
        TipoPagamento tipoPagamento = new TipoPagamento();

        tipoPagamento.setId(1L);
        tipoPagamento.setTipoPagamento("PIX");

        assertThat(tipoPagamento.getId()).isEqualTo(1L);
        assertThat(tipoPagamento.getTipoPagamento()).isEqualTo("PIX");
    }
}

