package com.sm.be.smartbank.repository.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.be.smartbank.model.entity.operazione.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
