package org.example.analytics.controller;

import org.example.analytics.model.TipoPagamento;
import org.example.analytics.service.TipoPagamentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-pagamento")
public class TipoPagamentoController {

    TipoPagamentoService tipoPagamentoService;

    public TipoPagamentoController(TipoPagamentoService tipoPagamentoService) {
        this.tipoPagamentoService = tipoPagamentoService;
    }

    @GetMapping
    public List<TipoPagamento> getAllTiposPagamento() {
        return tipoPagamentoService.buscarTodosTipoPagamento();
    }

    @GetMapping("/{id}")
    public TipoPagamento getTipoPagamentoById(@PathVariable Long id) {
        return tipoPagamentoService.buscarTipoPagamentoById(id);
    }

    @PostMapping
    public TipoPagamento createTipoPagamento(@RequestBody TipoPagamento tipoPagamento) {
        return tipoPagamentoService.incluirTipoPagamento(tipoPagamento);
    }

    @PutMapping
    public TipoPagamento alterTipoPagamento(@RequestBody TipoPagamento tipoPagamento) {
        return tipoPagamentoService.alterarTipoPagamento(tipoPagamento);
    }

    @DeleteMapping("/{id}")
    public void deleteTipoPagamentoById(@RequestBody Long id) {
        tipoPagamentoService.excluirTipoPagamento(id);
    }


}
