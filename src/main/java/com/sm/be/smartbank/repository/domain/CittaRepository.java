package com.sm.be.smartbank.repository.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.be.smartbank.model.entity.localita.Citta;

public interface CittaRepository extends JpaRepository<Citta, Long> {

}
