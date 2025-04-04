package com.sm.be.smartbank.repository.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.be.smartbank.model.entity.atm.Atm;

public interface AtmRepository extends JpaRepository<Atm, Long> {

	Optional<Atm> findById(Long id);

}
