package org.example.analytics.repository;

import org.example.analytics.model.TransacaoCompleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoCompletaRepository extends JpaRepository<TransacaoCompleta, Long> {

}
