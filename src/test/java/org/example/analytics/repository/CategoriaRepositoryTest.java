package org.example.analytics.repository;

import org.example.analytics.model.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository repository;

    @Test
    void deveSalvarEBuscarCategoriaPorId() {
        Categoria categoria = new Categoria();
        categoria.setTipo_categoria("Lazer");

        Categoria salva = repository.save(categoria);
        Optional<Categoria> encontrada = repository.findById(salva.getId());

        assertThat(salva.getId()).isNotNull();
        assertThat(encontrada).isPresent();
        assertThat(encontrada.orElseThrow().getTipo_categoria()).isEqualTo("Lazer");
    }
}

