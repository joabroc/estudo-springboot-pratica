package org.example.analytics.controller;

import org.example.analytics.model.TransacaoCompleta;
import org.example.analytics.service.TransacaoCompletaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacao-completa")
public class TransacaoCompletaController {

    private final TransacaoCompletaService transacaoCompletaService;

    public TransacaoCompletaController(TransacaoCompletaService transacaoCompletaService) {
        this.transacaoCompletaService = transacaoCompletaService;
    }

    @GetMapping
    public List<TransacaoCompleta> getAllTransacaoCompleta() {
        return transacaoCompletaService.listarTodas();
    }

    @GetMapping("/{id}")
    public TransacaoCompleta getTransacaoCompletaById(@PathVariable Long id) {
        return transacaoCompletaService.buscarTransacaoCompletaPorId(id);
    }
}
