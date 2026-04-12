package org.example.analytics.service;

import org.example.analytics.exception.NotFoundException;
import org.example.analytics.model.TransacaoCompleta;
import org.example.analytics.repository.TransacaoCompletaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoCompletaService {

    private static final Logger log = LoggerFactory.getLogger(TransacaoCompletaService.class);

    private final TransacaoCompletaRepository transacaoCompletaRepository;

    public TransacaoCompletaService(TransacaoCompletaRepository transacaoCompletaRepository) {
        this.transacaoCompletaRepository = transacaoCompletaRepository;
    }

    public List<TransacaoCompleta> listarTodas() {
        List<TransacaoCompleta> transacoes = transacaoCompletaRepository.findAll();
        log.debug("event=service.call entity=transacaoCompleta operation=list outcome=success count={}", transacoes.size());
        return transacoes;
    }

    public TransacaoCompleta buscarTransacaoCompletaPorId(Long id) {
        return transacaoCompletaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("event=service.call entity=transacaoCompleta operation=getById outcome=not_found id={}", id);
                    return new NotFoundException("Transação não encontrada com id: " + id);
                });
    }

}
