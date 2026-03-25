package org.example.analytics.Service;

import org.example.analytics.Model.TransacaoCompleta;
import org.example.analytics.Repository.TransacaoCompletaRepository;
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

    public Optional<TransacaoCompleta> buscarTransacaoCompletaPorId(Long id) {
        return transacaoCompletaRepository.findById(id);
    }
}
