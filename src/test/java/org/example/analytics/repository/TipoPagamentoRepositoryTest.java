package org.example.analytics.repository;

import org.example.analytics.model.TipoPagamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TipoPagamentoRepositoryTest {

    @Autowired
    private TipoPagamentoRepository repository;

    @Test
    void deveSalvarEBuscarTipoPagamentoPorId() {
        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setTipoPagamento("PIX");

        TipoPagamento salvo = repository.save(tipoPagamento);
        Optional<TipoPagamento> encontrado = repository.findById(salvo.getId());

        assertThat(salvo.getId()).isNotNull();
        assertThat(encontrado).isPresent();
        assertThat(encontrado.orElseThrow().getTipoPagamento()).isEqualTo("PIX");
    }
}

