package org.example.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.analytics.model.Transacao;
import org.example.analytics.service.TransacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
@Tag(name = "Transações", description = "Operações relacionadas às transações")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(summary = "Listar todas as transações", description = "Retorna a lista completa de transações cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<Transacao> getAllTransacoes() {
        return transacaoService.listarTodas();
    }

    @Operation(summary = "Buscar transação por ID", description = "Retorna uma transação específica com base no seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @GetMapping("/{id}")
    public Transacao getTransacaoById(@Parameter(description = "ID da transação") @PathVariable Long id) {
        return transacaoService.buscarTransacaoPorId(id);
    }

    @Operation(summary = "Criar nova transação", description = "Cadastra uma nova transação no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public Transacao postTransacao(@RequestBody Transacao transacao) {
        return transacaoService.incluirTransacao(transacao);
    }

    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @PutMapping
    public Transacao putTransacao(@RequestBody Transacao transacao) {
        return transacaoService.atualizarTransacao(transacao);
    }

    @Operation(summary = "Excluir transação", description = "Remove uma transação pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @DeleteMapping("/{id}")
    public void deleteTransacao(@Parameter(description = "ID da transação a ser excluída") @PathVariable Long id) {
        transacaoService.excluirTransacao(id);
    }

}
