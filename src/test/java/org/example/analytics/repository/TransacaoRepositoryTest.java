package org.example.analytics.repository;

import org.example.analytics.model.Transacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransacaoRepositoryTest {

    @Autowired
    private TransacaoRepository repository;

    @Test
    void deveSalvarEBuscarTransacaoPorId() {
        Transacao transacao = new Transacao();
        transacao.setEstabelecimentoId(123L);
        transacao.setValor(199.99);
        transacao.setTipoPagamentoId(2L);
        transacao.setData("2026-03-31");

        Transacao salva = repository.save(transacao);
        Optional<Transacao> encontrada = repository.findById(salva.getId());

        assertThat(salva.getId()).isNotNull();
        assertThat(encontrada).isPresent();
        assertThat(encontrada.orElseThrow().getEstabelecimentoId()).isEqualTo(123L);
        assertThat(encontrada.orElseThrow().getValor()).isEqualTo(199.99);
        assertThat(encontrada.orElseThrow().getTipoPagamentoId()).isEqualTo(2L);
        assertThat(encontrada.orElseThrow().getData()).isEqualTo("2026-03-31");
    }
}

