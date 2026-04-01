package org.example.analytics.service;

import org.example.analytics.exception.NotFoundException;
import org.example.analytics.model.Transacao;
import org.example.analytics.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository repository;

    @InjectMocks
    private TransacaoService service;

    @Test
    void listarTodasDeveRetornarTodasAsTransacoes() {
        Transacao transacao = criarTransacao(1L, 10L, 150.75, 2L, "2026-03-31");
        when(repository.findAll()).thenReturn(List.of(transacao));

        List<Transacao> resultado = service.listarTodas();

        assertThat(resultado)
                .hasSize(1)
                .containsExactly(transacao);
        verify(repository).findAll();
    }

    @Test
    void incluirTransacaoDeveSalvarERetornarEntidade() {
        Transacao transacao = criarTransacao(null, 20L, 99.99, 3L, "2026-04-01");
        Transacao salva = criarTransacao(5L, 20L, 99.99, 3L, "2026-04-01");
        when(repository.save(transacao)).thenReturn(salva);

        Transacao resultado = service.incluirTransacao(transacao);

        assertThat(resultado).isSameAs(salva);
        verify(repository).save(transacao);
    }

    @Test
    void atualizarTransacaoDevePersistirAlteracoes() {
        Transacao transacao = criarTransacao(7L, 30L, 240.0, 1L, "2026-04-02");
        when(repository.save(transacao)).thenReturn(transacao);

        Transacao resultado = service.atualizarTransacao(transacao);

        assertThat(resultado).isSameAs(transacao);
        verify(repository).save(transacao);
    }

    @Test
    void excluirTransacaoDeveDelegarExclusaoAoRepositorio() {
        service.excluirTransacao(11L);

        verify(repository).deleteById(11L);
    }

    @Test
    void buscarTransacaoPorIdDeveRetornarEntidadeQuandoEncontrada() {
        Transacao transacao = criarTransacao(12L, 99L, 321.45, 4L, "2026-04-03");
        when(repository.findById(12L)).thenReturn(Optional.of(transacao));

        Transacao resultado = service.buscarTransacaoPorId(12L);

        assertThat(resultado).isSameAs(transacao);
        verify(repository).findById(12L);
    }

    @Test
    void buscarTransacaoPorIdDeveLancarExcecaoQuandoNaoEncontrada() {
        when(repository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarTransacaoPorId(404L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Transação não encontrada com id: 404");

        verify(repository).findById(404L);
    }

    private Transacao criarTransacao(Long id, Long estabelecimentoId, Double valor, Long tipoPagamentoId, String data) {
        Transacao transacao = new Transacao();
        transacao.setId(id);
        transacao.setEstabelecimentoId(estabelecimentoId);
        transacao.setValor(valor);
        transacao.setTipoPagamentoId(tipoPagamentoId);
        transacao.setData(data);
        return transacao;
    }
}

