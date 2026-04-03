package org.example.analytics.service;

import org.example.analytics.model.TipoPagamento;
import org.example.analytics.repository.TipoPagamentoRepository;
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
class TipoPagamentoServiceTest {

    @Mock
    private TipoPagamentoRepository repository;

    @InjectMocks
    private TipoPagamentoService service;

    @Test
    void buscarTodosTipoPagamentoDeveRetornarListaDoRepositorio() {
        TipoPagamento tipo = criarTipoPagamento(1L, "PIX");
        when(repository.findAll()).thenReturn(List.of(tipo));

        List<TipoPagamento> resultado = service.buscarTodosTipoPagamento();

        assertThat(resultado).hasSize(1).containsExactly(tipo);
        verify(repository).findAll();
    }

    @Test
    void buscarTipoPagamentoByIdDeveRetornarReferenciaDoRepositorio() {
        TipoPagamento tipo = criarTipoPagamento(2L, "Credito");
        when(repository.getReferenceById(2L)).thenReturn(tipo);

        TipoPagamento resultado = service.buscarTipoPagamentoById(2L);

        assertThat(resultado).isSameAs(tipo);
        verify(repository).getReferenceById(2L);
    }

    @Test
    void incluirTipoPagamentoDeveSalvarERetornarEntidade() {
        TipoPagamento entrada = criarTipoPagamento(null, "Debito");
        TipoPagamento salvo = criarTipoPagamento(3L, "Debito");
        when(repository.save(entrada)).thenReturn(salvo);

        TipoPagamento resultado = service.incluirTipoPagamento(entrada);

        assertThat(resultado).isSameAs(salvo);
        verify(repository).save(entrada);
    }

    @Test
    void alterarTipoPagamentoDeveSalvarERetornarEntidade() {
        TipoPagamento tipo = criarTipoPagamento(4L, "Dinheiro");
        when(repository.save(tipo)).thenReturn(tipo);

        TipoPagamento resultado = service.alterarTipoPagamento(tipo);

        assertThat(resultado).isSameAs(tipo);
        verify(repository).save(tipo);
    }

    @Test
    void excluirTipoPagamentoDeveDelegarExclusaoAoRepositorio() {
        service.excluirTipoPagamento(5L);

        verify(repository).deleteById(5L);
    }

    private TipoPagamento criarTipoPagamento(Long id, String descricao) {
        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(id);
        tipoPagamento.setTipoPagamento(descricao);
        return tipoPagamento;
    }
}

