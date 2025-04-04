package com.sm.be.smartbank.repository.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sm.be.smartbank.model.entity.personale.Profilo;
import com.sm.be.smartbank.repository.domain.statement.ProfiloStatement;

public interface ProfiloRepository extends JpaRepository<Profilo, Long> {

	@Query(value = ProfiloStatement.FIND_BY_USERNAME)
	Profilo findByUsername(String username);

	boolean existsByIdUtente(Long idUtente);

	boolean existsByCodiceFiscale(String codiceFiscale);

	boolean existsByTelefono(String telefono);

	boolean existsByTelefonoAndIdProfiloNot(String telefono, Long idProfilo);

}
