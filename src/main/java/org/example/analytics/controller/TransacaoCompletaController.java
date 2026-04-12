package org.example.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.analytics.model.TransacaoCompleta;
import org.example.analytics.service.TransacaoCompletaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao-completa")
@Tag(name = "Transações Completas", description = "Consulta de transações com dados completos (view consolidada)")
public class TransacaoCompletaController {

    private static final Logger log = LoggerFactory.getLogger(TransacaoCompletaController.class);

    private final TransacaoCompletaService transacaoCompletaService;

    public TransacaoCompletaController(TransacaoCompletaService transacaoCompletaService) {
        this.transacaoCompletaService = transacaoCompletaService;
    }

    @Operation(summary = "Listar todas as transações completas", description = "Retorna a lista de transações com todos os dados consolidados (categoria, estabelecimento e tipo de pagamento)")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<TransacaoCompleta> getAllTransacaoCompleta() {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=transacaoCompleta operation=list outcome=start");
        List<TransacaoCompleta> transacoes = transacaoCompletaService.listarTodas();
        log.info("event=api.request entity=transacaoCompleta operation=list outcome=success durationMs={} count={}",
                System.currentTimeMillis() - start, transacoes.size());
        return transacoes;
    }

    @Operation(summary = "Buscar transação completa por ID", description = "Retorna uma transação completa específica com base no seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação completa encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @GetMapping("/{id}")
    public TransacaoCompleta getTransacaoCompletaById(@Parameter(description = "ID da transação") @PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=transacaoCompleta operation=getById outcome=start id={}", id);
        TransacaoCompleta transacao = transacaoCompletaService.buscarTransacaoCompletaPorId(id);
        log.info("event=api.request entity=transacaoCompleta operation=getById outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, id);
        return transacao;
    }
}
