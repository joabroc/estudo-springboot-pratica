package org.example.analytics.service;

import org.example.analytics.model.TipoPagamento;
import org.example.analytics.repository.TipoPagamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPagamentoService {

    private static final Logger log = LoggerFactory.getLogger(TipoPagamentoService.class);

    private final TipoPagamentoRepository tipoPagamentoRepository;

    public TipoPagamentoService(TipoPagamentoRepository tipoPagamentoRepository) {
        this.tipoPagamentoRepository = tipoPagamentoRepository;
    }

    public List<TipoPagamento> buscarTodosTipoPagamento() {
        List<TipoPagamento> tiposPagamento = tipoPagamentoRepository.findAll();
        log.debug("event=service.call entity=tipoPagamento operation=list outcome=success count={}", tiposPagamento.size());
        return tiposPagamento;
    }

    public TipoPagamento buscarTipoPagamentoById(Long id) {
        TipoPagamento tipoPagamento = tipoPagamentoRepository.getReferenceById(id);
        log.debug("event=service.call entity=tipoPagamento operation=getById outcome=success id={}", id);
        return tipoPagamento;
    }

    public TipoPagamento incluirTipoPagamento(TipoPagamento tipoPagamento) {
        TipoPagamento tipoPagamentoSalvo = tipoPagamentoRepository.save(tipoPagamento);
        log.info("event=service.call entity=tipoPagamento operation=create outcome=success id={}", tipoPagamentoSalvo.getId());
        return tipoPagamentoSalvo;
    }

    public TipoPagamento alterarTipoPagamento(TipoPagamento tipoPagamento) {
        TipoPagamento tipoPagamentoAtualizado = tipoPagamentoRepository.save(tipoPagamento);
        log.info("event=service.call entity=tipoPagamento operation=update outcome=success id={}", tipoPagamentoAtualizado.getId());
        return tipoPagamentoAtualizado;
    }

    public void excluirTipoPagamento(Long id) {
        tipoPagamentoRepository.deleteById(id);
        log.info("event=service.call entity=tipoPagamento operation=delete outcome=success id={}", id);
    }
}
