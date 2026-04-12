package org.example.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.analytics.model.Estabelecimento;
import org.example.analytics.service.EstabelecimentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estabelecimentos")
@Tag(name = "Estabelecimentos", description = "Operações relacionadas aos estabelecimentos")
public class EstabelecimentoController {

    EstabelecimentoService estabelecimentoService;

    public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    @Operation(summary = "Listar todos os estabelecimentos", description = "Retorna a lista completa de estabelecimentos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<Estabelecimento> getAllCategorias() {
        return estabelecimentoService.listarTodos();
    }

    @Operation(summary = "Buscar estabelecimento por ID", description = "Retorna um estabelecimento específico com base no seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    @GetMapping("/{id}")
    public Estabelecimento getEstabelecimentobyId(@Parameter(description = "ID do estabelecimento") @PathVariable Long id) {
        return estabelecimentoService.listarEstabelecimentoById(id);
    }

    @Operation(summary = "Criar novo estabelecimento", description = "Cadastra um novo estabelecimento no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public Estabelecimento postEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        return estabelecimentoService.incluirEstabelecimento(estabelecimento);
    }

    @Operation(summary = "Atualizar estabelecimento", description = "Atualiza os dados de um estabelecimento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    @PutMapping
    public Estabelecimento putEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        return estabelecimentoService.alterarEstabelecimento(estabelecimento);
    }

    @Operation(summary = "Excluir estabelecimento", description = "Remove um estabelecimento pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteEstabelecimentoById(@Parameter(description = "ID do estabelecimento a ser excluído") @PathVariable long id) {
        estabelecimentoService.excluirEstabelecimento(id);
    }

}
