package org.example.analytics.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EstabelecimentoTest {

    @Test
    void gettersESettersDevemManipularTodosOsCampos() {
        Estabelecimento estabelecimento = new Estabelecimento();
        LocalDateTime dataCadastro = LocalDateTime.of(2026, 4, 3, 12, 0);

        estabelecimento.setId(1L);
        estabelecimento.setNome("Loja Centro");
        estabelecimento.setCategoryId("2");
        estabelecimento.setDataCadastro(dataCadastro);

        assertThat(estabelecimento.getId()).isEqualTo(1L);
        assertThat(estabelecimento.getNome()).isEqualTo("Loja Centro");
        assertThat(estabelecimento.getCategoryId()).isEqualTo("2");
        assertThat(estabelecimento.getDataCadastro()).isEqualTo(dataCadastro);
    }
}

