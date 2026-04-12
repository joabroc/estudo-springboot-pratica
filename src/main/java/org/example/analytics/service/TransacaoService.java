package org.example.analytics.service;

import org.example.analytics.exception.NotFoundException;
import org.example.analytics.model.Transacao;
import org.example.analytics.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public List<Transacao> listarTodas() {
        List<Transacao> transacoes = repository.findAll();
        log.debug("event=service.call entity=transacao operation=list outcome=success count={}", transacoes.size());
        return transacoes;
    }

    public Transacao incluirTransacao(Transacao transacao) {
        Transacao transacaoSalva = repository.save(transacao);
        log.info("event=service.call entity=transacao operation=create outcome=success id={}", transacaoSalva.getId());
        return transacaoSalva;
    }

    public Transacao atualizarTransacao(Transacao transacao) {
        Transacao transacaoAtualizada = repository.save(transacao);
        log.info("event=service.call entity=transacao operation=update outcome=success id={}", transacaoAtualizada.getId());
        return transacaoAtualizada;
    }

    public void excluirTransacao(Long id) {
        repository.deleteById(id);
        log.info("event=service.call entity=transacao operation=delete outcome=success id={}", id);
    }

    public Transacao buscarTransacaoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("event=service.call entity=transacao operation=getById outcome=not_found id={}", id);
                    return new NotFoundException("Transação não encontrada com id: " + id);
                });
    }


}
