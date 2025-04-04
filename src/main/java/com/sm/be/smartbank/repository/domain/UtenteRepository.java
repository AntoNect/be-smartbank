package com.sm.be.smartbank.repository.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.be.smartbank.model.entity.personale.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	Utente findByUsername(String username);

	Utente findByEmail(String username);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}
