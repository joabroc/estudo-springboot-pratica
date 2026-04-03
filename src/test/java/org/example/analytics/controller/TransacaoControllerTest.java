package org.example.analytics.controller;

import org.example.analytics.model.Transacao;
import org.example.analytics.service.TransacaoService;
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
class TransacaoControllerTest {

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private TransacaoController controller;

    @Test
    void getAllTransacoesDeveRetornarListaDoService() {
        Transacao transacao = criarTransacao(1L);
        when(transacaoService.listarTodas()).thenReturn(List.of(transacao));

        List<Transacao> resultado = controller.getAllTransacoes();

        assertThat(resultado)
                .hasSize(1)
                .containsExactly(transacao);
        verify(transacaoService).listarTodas();
    }

    @Test
    void getTransacaoByIdDeveRetornarTransacaoDoService() {
        Transacao transacao = criarTransacao(2L);
        when(transacaoService.buscarTransacaoPorId(2L)).thenReturn(transacao);

        Transacao resultado = controller.getTransacaoById(2L);

        assertThat(resultado).isSameAs(transacao);
        verify(transacaoService).buscarTransacaoPorId(2L);
    }

    @Test
    void postTransacaoDeveDelegarInclusaoAoService() {
        Transacao entrada = criarTransacao(null);
        Transacao salva = criarTransacao(3L);
        when(transacaoService.incluirTransacao(entrada)).thenReturn(salva);

        Transacao resultado = controller.postTransacao(entrada);

        assertThat(resultado).isSameAs(salva);
        verify(transacaoService).incluirTransacao(entrada);
    }

    @Test
    void putTransacaoDeveDelegarAtualizacaoAoService() {
        Transacao transacao = criarTransacao(4L);
        when(transacaoService.atualizarTransacao(transacao)).thenReturn(transacao);

        Transacao resultado = controller.putTransacao(transacao);

        assertThat(resultado).isSameAs(transacao);
        verify(transacaoService).atualizarTransacao(transacao);
    }

    @Test
    void deleteTransacaoDeveDelegarExclusaoAoService() {
        controller.deleteTransacao(5L);

        verify(transacaoService).excluirTransacao(5L);
    }

    private Transacao criarTransacao(Long id) {
        Transacao transacao = new Transacao();
        transacao.setId(id);
        transacao.setEstabelecimentoId(100L);
        transacao.setValor(88.0);
        transacao.setTipoPagamentoId(1L);
        transacao.setData("2026-03-31");
        return transacao;
    }
}

