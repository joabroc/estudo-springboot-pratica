package org.example.analytics.service;

import org.example.analytics.exception.NotFoundException;
import org.example.analytics.model.TransacaoCompleta;
import org.example.analytics.repository.TransacaoCompletaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoCompletaServiceTest {

    @Mock
    private TransacaoCompletaRepository repository;

    @InjectMocks
    private TransacaoCompletaService service;

    @Test
    void listarTodasDeveRetornarTodasAsTransacoesCompletas() {
        TransacaoCompleta transacao = criarTransacaoCompleta(1L);
        when(repository.findAll()).thenReturn(List.of(transacao));

        List<TransacaoCompleta> resultado = service.listarTodas();

        assertThat(resultado)
                .hasSize(1)
                .containsExactly(transacao);
        verify(repository).findAll();
    }

    @Test
    void buscarTransacaoCompletaPorIdDeveRetornarEntidadeQuandoEncontrada() {
        TransacaoCompleta transacao = criarTransacaoCompleta(10L);
        when(repository.findById(10L)).thenReturn(Optional.of(transacao));

        TransacaoCompleta resultado = service.buscarTransacaoCompletaPorId(10L);

        assertThat(resultado).isSameAs(transacao);
        verify(repository).findById(10L);
    }

    @Test
    void buscarTransacaoCompletaPorIdDeveLancarExcecaoQuandoNaoEncontrada() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarTransacaoCompletaPorId(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Transação não encontrada com id: 999");

        verify(repository).findById(999L);
    }

    private TransacaoCompleta criarTransacaoCompleta(Long id) {
        TransacaoCompleta transacao = new TransacaoCompleta();
        transacao.setId(id);
        transacao.setEstabelecimento("Loja Centro");
        transacao.setCategoria("Alimentação");
        transacao.setValor(57.8);
        transacao.setFormaPagamento("Crédito");
        transacao.setData(LocalDateTime.of(2026, 3, 31, 14, 30));
        return transacao;
    }
}

