package org.example.analytics.controller;

import org.example.analytics.Model.Transacao;
import org.example.analytics.Service.TransacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public List<Transacao> getAllTransacoes() {
        return transacaoService.listarTodas();
    }

    @GetMapping("/{id}")
    public Transacao getTransacaoById(@PathVariable Long id) {
        return transacaoService.buscarTransacaoPorId(id)
                .orElse(null);
    }

    @PostMapping
    public Transacao postTransacao(@RequestParam Transacao transacao) {
        return transacaoService.incluirTransacao(transacao);
    }
}
