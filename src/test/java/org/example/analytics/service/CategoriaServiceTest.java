package org.example.analytics.service;

import org.example.analytics.model.Categoria;
import org.example.analytics.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;

    @Test
    void listarTodasDeveRetornarTodasAsCategorias() {
        Categoria categoria = criarCategoria(1L, "Alimentacao");
        when(repository.findAll()).thenReturn(List.of(categoria));

        List<Categoria> resultado = service.listarTodas();

        assertThat(resultado)
                .hasSize(1)
                .containsExactly(categoria);
        verify(repository).findAll();
    }

    @Test
    void listarCategoriaByIdDeveRetornarReferenciaDoRepositorio() {
        Categoria categoria = criarCategoria(2L, "Transporte");
        when(repository.getReferenceById(2L)).thenReturn(categoria);

        Categoria resultado = service.listarCategoriaById(2L);

        assertThat(resultado).isSameAs(categoria);
        verify(repository).getReferenceById(2L);
    }

    @Test
    void incluirCategoriaDeveSalvarERetornarEntidade() {
        Categoria entrada = criarCategoria(null, "Saude");
        Categoria salva = criarCategoria(3L, "Saude");
        when(repository.save(entrada)).thenReturn(salva);

        Categoria resultado = service.incluirCategoria(entrada);

        assertThat(resultado).isSameAs(salva);
        verify(repository).save(entrada);
    }

    @Test
    void alterarCategoriaDeveSalvarERetornarEntidade() {
        Categoria categoria = criarCategoria(4L, "Mercado");
        when(repository.save(categoria)).thenReturn(categoria);

        Categoria resultado = service.alterarCategoria(categoria);

        assertThat(resultado).isSameAs(categoria);
        verify(repository).save(categoria);
    }

    @Test
    void excluirCategoriaDeveDelegarExclusaoAoRepositorio() {
        service.excluirCategoria(5L);

        verify(repository).deleteById(5L);
    }

    private Categoria criarCategoria(Long id, String tipo) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setTipo_categoria(tipo);
        return categoria;
    }
}

