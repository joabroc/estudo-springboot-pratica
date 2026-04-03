package org.example.analytics.service;

import org.example.analytics.model.Estabelecimento;
import org.example.analytics.repository.EstabelecimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;

    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    public List<Estabelecimento> listarTodos() {
        return estabelecimentoRepository.findAll();
    }

    public Estabelecimento listarEstabelecimentoById(Long id) {
        return estabelecimentoRepository.getReferenceById(id);
    }

    public Estabelecimento incluirEstabelecimento(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento);
    }

    public Estabelecimento alterarEstabelecimento(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento);
    }

    public void excluirEstabelecimento(Long id) {
        estabelecimentoRepository.deleteById(id);
    }
}
