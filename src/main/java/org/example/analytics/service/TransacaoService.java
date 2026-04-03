package org.example.analytics.service;

import org.example.analytics.exception.NotFoundException;
import org.example.analytics.model.Transacao;
import org.example.analytics.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public List<Transacao> listarTodas() {
        return repository.findAll();
    }

    public Transacao incluirTransacao(Transacao transacao) {
        return repository.save(transacao);
    }

    public Transacao atualizarTransacao(Transacao transacao) {
        return repository.save(transacao);
    }

    public void excluirTransacao(Long id) {
        repository.deleteById(id);
    }

    public Transacao buscarTransacaoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada com id: " + id));
    }


}
