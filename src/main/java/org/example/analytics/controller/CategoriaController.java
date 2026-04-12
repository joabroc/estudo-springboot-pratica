package org.example.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.analytics.model.Categoria;
import org.example.analytics.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Operações relacionadas às categorias de transações")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Listar todas as categorias", description = "Retorna a lista completa de categorias cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.listarTodas();
    }

    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica com base no seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public Categoria getCategoriabyId(@Parameter(description = "ID da categoria") @PathVariable Long id) {
        return categoriaService.listarCategoriaById(id);
    }

    @Operation(summary = "Criar nova categoria", description = "Cadastra uma nova categoria no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public Categoria postCategoria(@RequestBody Categoria categoria) {
        return categoriaService.incluirCategoria(categoria);
    }

    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping
    public Categoria putCategoria(@RequestBody Categoria categoria) {
        return categoriaService.alterarCategoria(categoria);
    }

    @Operation(summary = "Excluir categoria", description = "Remove uma categoria pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping
    public void deleteCategoria(@Parameter(description = "ID da categoria a ser excluída") @RequestParam Long id) {
        categoriaService.excluirCategoria(id);
    }
}
