package org.example.analytics.service;

import org.example.analytics.model.Categoria;
import org.example.analytics.repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private static final Logger log = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        log.debug("event=service.call entity=categoria operation=list outcome=success count={}", categorias.size());
        return categorias;
    }

    public Categoria listarCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.getReferenceById(id);
        log.debug("event=service.call entity=categoria operation=getById outcome=success id={}", id);
        return categoria;
    }

    public Categoria incluirCategoria(Categoria categoria) {
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        log.info("event=service.call entity=categoria operation=create outcome=success id={}", categoriaSalva.getId());
        return categoriaSalva;
    }

    public Categoria alterarCategoria(Categoria categoria) {
        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        log.info("event=service.call entity=categoria operation=update outcome=success id={}", categoriaAtualizada.getId());
        return categoriaAtualizada;
    }

    public void excluirCategoria(Long id) {
        categoriaRepository.deleteById(id);
        log.info("event=service.call entity=categoria operation=delete outcome=success id={}", id);
    }

}
