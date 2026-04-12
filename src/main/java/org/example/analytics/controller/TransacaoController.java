package org.example.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.analytics.model.Transacao;
import org.example.analytics.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
@Tag(name = "Transações", description = "Operações relacionadas às transações")
public class TransacaoController {

    private static final Logger log = LoggerFactory.getLogger(TransacaoController.class);

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(summary = "Listar todas as transações", description = "Retorna a lista completa de transações cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<Transacao> getAllTransacoes() {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=transacao operation=list outcome=start");
        List<Transacao> transacoes = transacaoService.listarTodas();
        log.info("event=api.request entity=transacao operation=list outcome=success durationMs={} count={}",
                System.currentTimeMillis() - start, transacoes.size());
        return transacoes;
    }

    @Operation(summary = "Buscar transação por ID", description = "Retorna uma transação específica com base no seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @GetMapping("/{id}")
    public Transacao getTransacaoById(@Parameter(description = "ID da transação") @PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=transacao operation=getById outcome=start id={}", id);
        Transacao transacao = transacaoService.buscarTransacaoPorId(id);
        log.info("event=api.request entity=transacao operation=getById outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, id);
        return transacao;
    }

    @Operation(summary = "Criar nova transação", description = "Cadastra uma nova transação no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public Transacao postTransacao(@RequestBody Transacao transacao) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=transacao operation=create outcome=start estabelecimentoId={} tipoPagamentoId={}",
                transacao.getEstabelecimentoId(), transacao.getTipoPagamentoId());
        Transacao transacaoCriada = transacaoService.incluirTransacao(transacao);
        log.info("event=api.request entity=transacao operation=create outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, transacaoCriada.getId());
        return transacaoCriada;
    }

    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @PutMapping
    public Transacao putTransacao(@RequestBody Transacao transacao) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=transacao operation=update outcome=start id={}", transacao.getId());
        Transacao transacaoAtualizada = transacaoService.atualizarTransacao(transacao);
        log.info("event=api.request entity=transacao operation=update outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, transacaoAtualizada.getId());
        return transacaoAtualizada;
    }

    @Operation(summary = "Excluir transação", description = "Remove uma transação pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @DeleteMapping("/{id}")
    public void deleteTransacao(@Parameter(description = "ID da transação a ser excluída") @PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=transacao operation=delete outcome=start id={}", id);
        transacaoService.excluirTransacao(id);
        log.info("event=api.request entity=transacao operation=delete outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, id);
    }

}
