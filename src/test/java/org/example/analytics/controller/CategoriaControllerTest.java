package org.example.analytics.controller;

import org.example.analytics.model.Categoria;
import org.example.analytics.service.CategoriaService;
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
class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController controller;

    @Test
    void getAllCategoriasDeveRetornarListaDoService() {
        Categoria categoria = criarCategoria(1L, "Lazer");
        when(categoriaService.listarTodas()).thenReturn(List.of(categoria));

        List<Categoria> resultado = controller.getAllCategorias();

        assertThat(resultado)
                .hasSize(1)
                .containsExactly(categoria);
        verify(categoriaService).listarTodas();
    }

    @Test
    void getCategoriabyIdDeveRetornarCategoriaDoService() {
        Categoria categoria = criarCategoria(2L, "Casa");
        when(categoriaService.listarCategoriaById(2L)).thenReturn(categoria);

        Categoria resultado = controller.getCategoriabyId(2L);

        assertThat(resultado).isSameAs(categoria);
        verify(categoriaService).listarCategoriaById(2L);
    }

    @Test
    void postCategoriaDeveDelegarInclusaoAoService() {
        Categoria entrada = criarCategoria(null, "Educacao");
        Categoria salva = criarCategoria(3L, "Educacao");
        when(categoriaService.incluirCategoria(entrada)).thenReturn(salva);

        Categoria resultado = controller.postCategoria(entrada);

        assertThat(resultado).isSameAs(salva);
        verify(categoriaService).incluirCategoria(entrada);
    }

    @Test
    void putCategoriaDeveDelegarAlteracaoAoService() {
        Categoria categoria = criarCategoria(4L, "Moradia");
        when(categoriaService.alterarCategoria(categoria)).thenReturn(categoria);

        Categoria resultado = controller.putCategoria(categoria);

        assertThat(resultado).isSameAs(categoria);
        verify(categoriaService).alterarCategoria(categoria);
    }

    @Test
    void deleteCategoriaDeveDelegarExclusaoAoService() {
        controller.deleteCategoria(5L);

        verify(categoriaService).excluirCategoria(5L);
    }

    private Categoria criarCategoria(Long id, String tipo) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setTipo_categoria(tipo);
        return categoria;
    }
}

