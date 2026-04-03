package org.example.analytics.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoriaTest {

    @Test
    void gettersESettersDevemManipularTodosOsCampos() {
        Categoria categoria = new Categoria();

        categoria.setId(1L);
        categoria.setTipo_categoria("Servicos");

        assertThat(categoria.getId()).isEqualTo(1L);
        assertThat(categoria.getTipo_categoria()).isEqualTo("Servicos");
    }
}

