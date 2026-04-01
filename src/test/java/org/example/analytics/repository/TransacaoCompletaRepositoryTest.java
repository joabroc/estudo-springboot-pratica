package org.example.analytics.repository;

import org.example.analytics.model.TransacaoCompleta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransacaoCompletaRepositoryTest {

    @Autowired
    private TransacaoCompletaRepository repository;

    @Test
    void deveSalvarEBuscarTransacaoCompletaPorId() {
        TransacaoCompleta transacao = new TransacaoCompleta();
        transacao.setId(1L);
        transacao.setEstabelecimento("Posto Central");
        transacao.setCategoria("Combustível");
        transacao.setValor(250.0);
        transacao.setFormaPagamento("Crédito");
        transacao.setData(LocalDateTime.of(2026, 3, 31, 18, 0));

        repository.save(transacao);
        Optional<TransacaoCompleta> encontrada = repository.findById(1L);

        assertThat(encontrada).isPresent();
        assertThat(encontrada.orElseThrow().getEstabelecimento()).isEqualTo("Posto Central");
        assertThat(encontrada.orElseThrow().getCategoria()).isEqualTo("Combustível");
        assertThat(encontrada.orElseThrow().getValor()).isEqualTo(250.0);
        assertThat(encontrada.orElseThrow().getFormaPagamento()).isEqualTo("Crédito");
        assertThat(encontrada.orElseThrow().getData()).isEqualTo(LocalDateTime.of(2026, 3, 31, 18, 0));
    }
}

