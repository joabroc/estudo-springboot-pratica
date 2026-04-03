package org.example.analytics.controller;

import org.example.analytics.model.TipoPagamento;
import org.example.analytics.service.TipoPagamentoService;
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
class TipoPagamentoControllerTest {

    @Mock
    private TipoPagamentoService tipoPagamentoService;

    @InjectMocks
    private TipoPagamentoController controller;

    @Test
    void getAllTiposPagamentoDeveRetornarListaDoService() {
        TipoPagamento tipoPagamento = criarTipoPagamento(1L, "PIX");
        when(tipoPagamentoService.buscarTodosTipoPagamento()).thenReturn(List.of(tipoPagamento));

        List<TipoPagamento> resultado = controller.getAllTiposPagamento();

        assertThat(resultado).hasSize(1).containsExactly(tipoPagamento);
        verify(tipoPagamentoService).buscarTodosTipoPagamento();
    }

    @Test
    void getTipoPagamentoByIdDeveRetornarEntidadeDoService() {
        TipoPagamento tipoPagamento = criarTipoPagamento(2L, "Credito");
        when(tipoPagamentoService.buscarTipoPagamentoById(2L)).thenReturn(tipoPagamento);

        TipoPagamento resultado = controller.getTipoPagamentoById(2L);

        assertThat(resultado).isSameAs(tipoPagamento);
        verify(tipoPagamentoService).buscarTipoPagamentoById(2L);
    }

    @Test
    void createTipoPagamentoDeveDelegarInclusaoAoService() {
        TipoPagamento entrada = criarTipoPagamento(null, "Debito");
        TipoPagamento salvo = criarTipoPagamento(3L, "Debito");
        when(tipoPagamentoService.incluirTipoPagamento(entrada)).thenReturn(salvo);

        TipoPagamento resultado = controller.createTipoPagamento(entrada);

        assertThat(resultado).isSameAs(salvo);
        verify(tipoPagamentoService).incluirTipoPagamento(entrada);
    }

    @Test
    void alterTipoPagamentoDeveDelegarAlteracaoAoService() {
        TipoPagamento tipoPagamento = criarTipoPagamento(4L, "Dinheiro");
        when(tipoPagamentoService.alterarTipoPagamento(tipoPagamento)).thenReturn(tipoPagamento);

        TipoPagamento resultado = controller.alterTipoPagamento(tipoPagamento);

        assertThat(resultado).isSameAs(tipoPagamento);
        verify(tipoPagamentoService).alterarTipoPagamento(tipoPagamento);
    }

    @Test
    void deleteTipoPagamentoByIdDeveDelegarExclusaoAoService() {
        controller.deleteTipoPagamentoById(5L);

        verify(tipoPagamentoService).excluirTipoPagamento(5L);
    }

    private TipoPagamento criarTipoPagamento(Long id, String descricao) {
        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(id);
        tipoPagamento.setTipoPagamento(descricao);
        return tipoPagamento;
    }
}

