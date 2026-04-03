package org.example.analytics.repository;

import jdk.jfr.Registered;
import org.example.analytics.model.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagamentoRepository extends JpaRepository<TipoPagamento, Long> {

}
