package org.example.analytics.service;

import org.example.analytics.model.Estabelecimento;
import org.example.analytics.repository.EstabelecimentoRepository;
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
class EstabelecimentoServiceTest {

    @Mock
    private EstabelecimentoRepository repository;

    @InjectMocks
    private EstabelecimentoService service;

    @Test
    void listarTodosDeveRetornarListaDoRepositorio() {
        Estabelecimento estabelecimento = criarEstabelecimento(1L, "Loja Centro", "1");
        when(repository.findAll()).thenReturn(List.of(estabelecimento));

        List<Estabelecimento> resultado = service.listarTodos();

        assertThat(resultado).hasSize(1).containsExactly(estabelecimento);
        verify(repository).findAll();
    }

    @Test
    void listarEstabelecimentoByIdDeveRetornarReferenciaDoRepositorio() {
        Estabelecimento estabelecimento = criarEstabelecimento(2L, "Mercado Sul", "2");
        when(repository.getReferenceById(2L)).thenReturn(estabelecimento);

        Estabelecimento resultado = service.listarEstabelecimentoById(2L);

        assertThat(resultado).isSameAs(estabelecimento);
        verify(repository).getReferenceById(2L);
    }

    @Test
    void incluirEstabelecimentoDeveSalvarERetornarEntidade() {
        Estabelecimento entrada = criarEstabelecimento(null, "Farmacia", "3");
        Estabelecimento salvo = criarEstabelecimento(3L, "Farmacia", "3");
        when(repository.save(entrada)).thenReturn(salvo);

        Estabelecimento resultado = service.incluirEstabelecimento(entrada);

        assertThat(resultado).isSameAs(salvo);
        verify(repository).save(entrada);
    }

    @Test
    void alterarEstabelecimentoDeveSalvarERetornarEntidade() {
        Estabelecimento estabelecimento = criarEstabelecimento(4L, "Padaria", "4");
        when(repository.save(estabelecimento)).thenReturn(estabelecimento);

        Estabelecimento resultado = service.alterarEstabelecimento(estabelecimento);

        assertThat(resultado).isSameAs(estabelecimento);
        verify(repository).save(estabelecimento);
    }

    @Test
    void excluirEstabelecimentoDeveDelegarExclusaoAoRepositorio() {
        service.excluirEstabelecimento(5L);

        verify(repository).deleteById(5L);
    }

    private Estabelecimento criarEstabelecimento(Long id, String nome, String categoriaId) {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(id);
        estabelecimento.setNome(nome);
        estabelecimento.setCategoryId(categoriaId);
        estabelecimento.setDataCadastro(LocalDateTime.of(2026, 4, 3, 10, 0));
        return estabelecimento;
    }
}

