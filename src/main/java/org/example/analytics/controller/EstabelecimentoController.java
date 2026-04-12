package org.example.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.analytics.model.Estabelecimento;
import org.example.analytics.service.EstabelecimentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estabelecimentos")
@Tag(name = "Estabelecimentos", description = "Operações relacionadas aos estabelecimentos")
public class EstabelecimentoController {

    private static final Logger log = LoggerFactory.getLogger(EstabelecimentoController.class);

    private final EstabelecimentoService estabelecimentoService;

    public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    @Operation(summary = "Listar todos os estabelecimentos", description = "Retorna a lista completa de estabelecimentos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<Estabelecimento> getAllCategorias() {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=estabelecimento operation=list outcome=start");
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarTodos();
        log.info("event=api.request entity=estabelecimento operation=list outcome=success durationMs={} count={}",
                System.currentTimeMillis() - start, estabelecimentos.size());
        return estabelecimentos;
    }

    @Operation(summary = "Buscar estabelecimento por ID", description = "Retorna um estabelecimento específico com base no seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    @GetMapping("/{id}")
    public Estabelecimento getEstabelecimentobyId(@Parameter(description = "ID do estabelecimento") @PathVariable Long id) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=estabelecimento operation=getById outcome=start id={}", id);
        Estabelecimento estabelecimento = estabelecimentoService.listarEstabelecimentoById(id);
        log.info("event=api.request entity=estabelecimento operation=getById outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, id);
        return estabelecimento;
    }

    @Operation(summary = "Criar novo estabelecimento", description = "Cadastra um novo estabelecimento no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public Estabelecimento postEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=estabelecimento operation=create outcome=start");
        Estabelecimento estabelecimentoCriado = estabelecimentoService.incluirEstabelecimento(estabelecimento);
        log.info("event=api.request entity=estabelecimento operation=create outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, estabelecimentoCriado.getId());
        return estabelecimentoCriado;
    }

    @Operation(summary = "Atualizar estabelecimento", description = "Atualiza os dados de um estabelecimento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    @PutMapping
    public Estabelecimento putEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=estabelecimento operation=update outcome=start id={}", estabelecimento.getId());
        Estabelecimento estabelecimentoAtualizado = estabelecimentoService.alterarEstabelecimento(estabelecimento);
        log.info("event=api.request entity=estabelecimento operation=update outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, estabelecimentoAtualizado.getId());
        return estabelecimentoAtualizado;
    }

    @Operation(summary = "Excluir estabelecimento", description = "Remove um estabelecimento pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteEstabelecimentoById(@Parameter(description = "ID do estabelecimento a ser excluído") @PathVariable long id) {
        long start = System.currentTimeMillis();
        log.info("event=api.request entity=estabelecimento operation=delete outcome=start id={}", id);
        estabelecimentoService.excluirEstabelecimento(id);
        log.info("event=api.request entity=estabelecimento operation=delete outcome=success durationMs={} id={}",
                System.currentTimeMillis() - start, id);
    }

}
