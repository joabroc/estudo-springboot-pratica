package org.example.analytics.controller;

import org.example.analytics.model.TransacaoCompleta;
import org.example.analytics.service.TransacaoCompletaService;
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
class TransacaoCompletaControllerTest {

    @Mock
    private TransacaoCompletaService service;

    @InjectMocks
    private TransacaoCompletaController controller;

    @Test
    void getAllTransacaoCompletaDeveRetornarListaDoService() {
        TransacaoCompleta transacao = criarTransacaoCompleta(1L);
        when(service.listarTodas()).thenReturn(List.of(transacao));

        List<TransacaoCompleta> resultado = controller.getAllTransacaoCompleta();

        assertThat(resultado)
                .hasSize(1)
                .containsExactly(transacao);
        verify(service).listarTodas();
    }

    @Test
    void getTransacaoCompletaByIdDeveRetornarEntidadeDoService() {
        TransacaoCompleta transacao = criarTransacaoCompleta(2L);
        when(service.buscarTransacaoCompletaPorId(2L)).thenReturn(transacao);

        TransacaoCompleta resultado = controller.getTransacaoCompletaById(2L);

        assertThat(resultado).isSameAs(transacao);
        verify(service).buscarTransacaoCompletaPorId(2L);
    }

    private TransacaoCompleta criarTransacaoCompleta(Long id) {
        TransacaoCompleta transacao = new TransacaoCompleta();
        transacao.setId(id);
        transacao.setEstabelecimento("Loja Teste");
        transacao.setCategoria("Serviços");
        transacao.setValor(45.0);
        transacao.setFormaPagamento("Débito");
        transacao.setData(LocalDateTime.of(2026, 3, 31, 9, 15));
        return transacao;
    }
}

