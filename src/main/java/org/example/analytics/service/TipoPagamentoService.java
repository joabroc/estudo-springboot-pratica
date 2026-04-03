package org.example.analytics.service;

import org.example.analytics.model.TipoPagamento;
import org.example.analytics.repository.TipoPagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPagamentoService {

    TipoPagamentoRepository tipoPagamentoRepository;

    public TipoPagamentoService(TipoPagamentoRepository tipoPagamentoRepository) {
        this.tipoPagamentoRepository = tipoPagamentoRepository;
    }

    public List<TipoPagamento> buscarTodosTipoPagamento() {
        return tipoPagamentoRepository.findAll();
    }

    public TipoPagamento buscarTipoPagamentoById(Long id) {
        return tipoPagamentoRepository.getReferenceById(id);
    }

    public TipoPagamento incluirTipoPagamento(TipoPagamento tipoPagamento) {
        return tipoPagamentoRepository.save(tipoPagamento);
    }

    public TipoPagamento alterarTipoPagamento(TipoPagamento tipoPagamento) {
        return tipoPagamentoRepository.save(tipoPagamento);
    }

    public void excluirTipoPagamento(Long id) {
        tipoPagamentoRepository.deleteById(id);
    }
}
