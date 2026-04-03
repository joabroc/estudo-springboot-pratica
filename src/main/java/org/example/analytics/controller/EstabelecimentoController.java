package org.example.analytics.controller;
import org.example.analytics.model.Estabelecimento;
import org.example.analytics.service.EstabelecimentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    EstabelecimentoService estabelecimentoService;

    public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    @GetMapping
    public List<Estabelecimento> getAllCategorias() {
        return estabelecimentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Estabelecimento getEstabelecimentobyId(@PathVariable Long id) {
        return estabelecimentoService.listarEstabelecimentoById(id);
    }

    @PostMapping
    public Estabelecimento postEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        return estabelecimentoService.incluirEstabelecimento(estabelecimento);
    }

    @PutMapping
    public Estabelecimento putEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        return estabelecimentoService.alterarEstabelecimento(estabelecimento);
    }

    @DeleteMapping("/{id}")
    public void deleteEstabelecimentoById(@PathVariable long id) {
        estabelecimentoService.excluirEstabelecimento(id);
    }

}
