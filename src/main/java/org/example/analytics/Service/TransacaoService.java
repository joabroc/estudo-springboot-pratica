package org.example.analytics.Service;

import org.example.analytics.Model.Transacao;
import org.example.analytics.Repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<Transacao> buscarTransacaoPorId(Long id) {
        return repository.findById(id);
    }

}
