package org.example.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.analytics.model.TipoPagamento;
import org.example.analytics.service.TipoPagamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-pagamento")
@Tag(name = "Tipos de Pagamento", description = "Operações relacionadas aos tipos de pagamento")
public class TipoPagamentoController {

    private static final Logger log = LoggerFactory.getLogger(TipoPagamentoController.class);

    private final TipoPagamentoService tipoPagamentoService;

    public TipoPagamentoController(TipoPagamentoService tipoPagamentoService) {
        this.tipoPagamentoService = tipoPagamentoService;
    }

    @Operation(summary = "Listar todos os tipos de pagamento", description = "Retorna a lista completa de tipos de pagamento cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<TipoPagamento> getAllTiposPagamento() {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=tipoPagamento operation=list outcome=start");
        List<TipoPagamento> tiposPagamento = tipoPagamentoService.buscarTodosTipoPagamento();
        log.info("event=api.request entity=tipoPagamento operation=list outcome=success durationMs={} count={}",
                System.currentTimeMillis() - start, tiposPagamento.size());
        return tiposPagamento;
    }

    @Operation(summary = "Buscar tipo de pagamento por ID", description = "Retorna um tipo de pagamento específico com base no seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de pagamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de pagamento não encontrado")
    })
    @GetMapping("/{id}")
    public TipoPagamento getTipoPagamentoById(@Parameter(description = "ID do tipo de pagamento") @PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=tipoPagamento operation=getById outcome=start id={}", id);
        TipoPagamento tipoPagamento = tipoPagamentoService.buscarTipoPagamentoById(id);
        log.info("event=api.request entity=tipoPagamento operation=getById outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, id);
        return tipoPagamento;
    }

    @Operation(summary = "Criar novo tipo de pagamento", description = "Cadastra um novo tipo de pagamento no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de pagamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public TipoPagamento createTipoPagamento(@RequestBody TipoPagamento tipoPagamento) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=tipoPagamento operation=create outcome=start");
        TipoPagamento tipoPagamentoCriado = tipoPagamentoService.incluirTipoPagamento(tipoPagamento);
        log.info("event=api.request entity=tipoPagamento operation=create outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, tipoPagamentoCriado.getId());
        return tipoPagamentoCriado;
    }

    @Operation(summary = "Atualizar tipo de pagamento", description = "Atualiza os dados de um tipo de pagamento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de pagamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de pagamento não encontrado")
    })
    @PutMapping
    public TipoPagamento alterTipoPagamento(@RequestBody TipoPagamento tipoPagamento) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=tipoPagamento operation=update outcome=start id={}", tipoPagamento.getId());
        TipoPagamento tipoPagamentoAtualizado = tipoPagamentoService.alterarTipoPagamento(tipoPagamento);
        log.info("event=api.request entity=tipoPagamento operation=update outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, tipoPagamentoAtualizado.getId());
        return tipoPagamentoAtualizado;
    }

    @Operation(summary = "Excluir tipo de pagamento", description = "Remove um tipo de pagamento pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de pagamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de pagamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteTipoPagamentoById(@Parameter(description = "ID do tipo de pagamento a ser excluído") @PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=tipoPagamento operation=delete outcome=start id={}", id);
        tipoPagamentoService.excluirTipoPagamento(id);
        log.info("event=api.request entity=tipoPagamento operation=delete outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, id);
    }


}
