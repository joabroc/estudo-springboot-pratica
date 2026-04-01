package org.example.analytics.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TransacaoCompletaTest {

    @Test
    void gettersESettersDevemManipularTodosOsCampos() {
        LocalDateTime data = LocalDateTime.of(2026, 3, 31, 11, 20);
        TransacaoCompleta transacao = new TransacaoCompleta();

        transacao.setId(10L);
        transacao.setEstabelecimento("Mercado XPTO");
        transacao.setCategoria("Mercado");
        transacao.setValor(59.9);
        transacao.setFormaPagamento("PIX");
        transacao.setData(data);

        assertThat(transacao.getId()).isEqualTo(10L);
        assertThat(transacao.getEstabelecimento()).isEqualTo("Mercado XPTO");
        assertThat(transacao.getCategoria()).isEqualTo("Mercado");
        assertThat(transacao.getValor()).isEqualTo(59.9);
        assertThat(transacao.getFormaPagamento()).isEqualTo("PIX");
        assertThat(transacao.getData()).isEqualTo(data);
    }
}

