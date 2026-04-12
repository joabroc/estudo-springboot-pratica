package org.example.analytics.service;

import org.example.analytics.model.Estabelecimento;
import org.example.analytics.repository.EstabelecimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstabelecimentoService {

    private static final Logger log = LoggerFactory.getLogger(EstabelecimentoService.class);

    private final EstabelecimentoRepository estabelecimentoRepository;

    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    public List<Estabelecimento> listarTodos() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();
        log.debug("event=service.call entity=estabelecimento operation=list outcome=success count={}", estabelecimentos.size());
        return estabelecimentos;
    }

    public Estabelecimento listarEstabelecimentoById(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.getReferenceById(id);
        log.debug("event=service.call entity=estabelecimento operation=getById outcome=success id={}", id);
        return estabelecimento;
    }

    public Estabelecimento incluirEstabelecimento(Estabelecimento estabelecimento) {
        Estabelecimento estabelecimentoSalvo = estabelecimentoRepository.save(estabelecimento);
        log.info("event=service.call entity=estabelecimento operation=create outcome=success id={}", estabelecimentoSalvo.getId());
        return estabelecimentoSalvo;
    }

    public Estabelecimento alterarEstabelecimento(Estabelecimento estabelecimento) {
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.save(estabelecimento);
        log.info("event=service.call entity=estabelecimento operation=update outcome=success id={}", estabelecimentoAtualizado.getId());
        return estabelecimentoAtualizado;
    }

    public void excluirEstabelecimento(Long id) {
        estabelecimentoRepository.deleteById(id);
        log.info("event=service.call entity=estabelecimento operation=delete outcome=success id={}", id);
    }
}
