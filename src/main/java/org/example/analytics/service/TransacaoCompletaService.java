package org.example.analytics.service;

import org.example.analytics.exception.NotFoundException;
import org.example.analytics.model.TransacaoCompleta;
import org.example.analytics.repository.TransacaoCompletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoCompletaService {

    private final TransacaoCompletaRepository transacaoCompletaRepository;

    public TransacaoCompletaService(TransacaoCompletaRepository transacaoCompletaRepository) {
        this.transacaoCompletaRepository = transacaoCompletaRepository;
    }

    public List<TransacaoCompleta> listarTodas() {
        return transacaoCompletaRepository.findAll();
    }

    public TransacaoCompleta buscarTransacaoCompletaPorId(Long id) {
        return transacaoCompletaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada com id: " + id));
    }
}
