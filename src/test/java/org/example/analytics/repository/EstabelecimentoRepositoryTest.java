package org.example.analytics.repository;

import org.example.analytics.model.Estabelecimento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EstabelecimentoRepositoryTest {

    @Autowired
    private EstabelecimentoRepository repository;

    @Test
    void deveSalvarEBuscarEstabelecimentoPorId() {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Mercado Bairro");
        estabelecimento.setCategoryId("2");
        estabelecimento.setDataCadastro(LocalDateTime.of(2026, 4, 3, 13, 15));

        Estabelecimento salvo = repository.save(estabelecimento);
        Optional<Estabelecimento> encontrado = repository.findById(salvo.getId());

        assertThat(salvo.getId()).isNotNull();
        assertThat(encontrado).isPresent();
        assertThat(encontrado.orElseThrow().getNome()).isEqualTo("Mercado Bairro");
        assertThat(encontrado.orElseThrow().getCategoryId()).isEqualTo("2");
        assertThat(encontrado.orElseThrow().getDataCadastro()).isEqualTo(LocalDateTime.of(2026, 4, 3, 13, 15));
    }
}

