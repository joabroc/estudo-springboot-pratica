package org.example.analytics.controller;

import org.example.analytics.model.Categoria;
import org.example.analytics.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Categoria getCategoriabyId(@PathVariable Long id) {
        return categoriaService.listarCategoriaById(id);
    }

    @PostMapping
    public Categoria postCategoria(@RequestBody Categoria categoria) {
        return categoriaService.incluirCategoria(categoria);
    }

    @PutMapping
    public Categoria putCategoria(@RequestBody Categoria categoria) {
        return categoriaService.alterarCategoria(categoria);
    }

    @DeleteMapping
    public void deleteCategoria(@RequestParam Long id) {
        categoriaService.excluirCategoria(id);
    }
}
