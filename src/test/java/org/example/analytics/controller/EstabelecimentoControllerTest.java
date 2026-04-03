package org.example.analytics.controller;

import org.example.analytics.model.Estabelecimento;
import org.example.analytics.service.EstabelecimentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstabelecimentoControllerTest {

    @Mock
    private EstabelecimentoService estabelecimentoService;

    @InjectMocks
    private EstabelecimentoController controller;

    @Test
    void getAllCategoriasDeveRetornarListaDoService() {
        Estabelecimento estabelecimento = criarEstabelecimento(1L, "Loja Norte", "1");
        when(estabelecimentoService.listarTodos()).thenReturn(List.of(estabelecimento));

        List<Estabelecimento> resultado = controller.getAllCategorias();

        assertThat(resultado).hasSize(1).containsExactly(estabelecimento);
        verify(estabelecimentoService).listarTodos();
    }

    @Test
    void getEstabelecimentobyIdDeveRetornarEntidadeDoService() {
        Estabelecimento estabelecimento = criarEstabelecimento(2L, "Mercado Sul", "2");
        when(estabelecimentoService.listarEstabelecimentoById(2L)).thenReturn(estabelecimento);

        Estabelecimento resultado = controller.getEstabelecimentobyId(2L);

        assertThat(resultado).isSameAs(estabelecimento);
        verify(estabelecimentoService).listarEstabelecimentoById(2L);
    }

    @Test
    void postEstabelecimentoDeveDelegarInclusaoAoService() {
        Estabelecimento entrada = criarEstabelecimento(null, "Farmacia", "3");
        Estabelecimento salvo = criarEstabelecimento(3L, "Farmacia", "3");
        when(estabelecimentoService.incluirEstabelecimento(entrada)).thenReturn(salvo);

        Estabelecimento resultado = controller.postEstabelecimento(entrada);

        assertThat(resultado).isSameAs(salvo);
        verify(estabelecimentoService).incluirEstabelecimento(entrada);
    }

    @Test
    void putEstabelecimentoDeveDelegarAlteracaoAoService() {
        Estabelecimento estabelecimento = criarEstabelecimento(4L, "Padaria", "4");
        when(estabelecimentoService.alterarEstabelecimento(estabelecimento)).thenReturn(estabelecimento);

        Estabelecimento resultado = controller.putEstabelecimento(estabelecimento);

        assertThat(resultado).isSameAs(estabelecimento);
        verify(estabelecimentoService).alterarEstabelecimento(estabelecimento);
    }

    @Test
    void deleteEstabelecimentoByIdDeveDelegarExclusaoAoService() {
        controller.deleteEstabelecimentoById(5L);

        verify(estabelecimentoService).excluirEstabelecimento(5L);
    }

    private Estabelecimento criarEstabelecimento(Long id, String nome, String categoriaId) {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(id);
        estabelecimento.setNome(nome);
        estabelecimento.setCategoryId(categoriaId);
        estabelecimento.setDataCadastro(LocalDateTime.of(2026, 4, 3, 11, 30));
        return estabelecimento;
    }
}

