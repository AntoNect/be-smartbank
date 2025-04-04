package com.sm.be.smartbank.repository.domain;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sm.be.smartbank.model.bo.DashBoardBO;
import com.sm.be.smartbank.model.entity.personale.Conto;
import com.sm.be.smartbank.repository.domain.statement.ContoStatement;

public interface ContoRepository extends JpaRepository<Conto, Long> {

	@Query(value = ContoStatement.FIND_BY_USERNAME)
	Optional<Conto> findByUsername(String username);
	
	@Query(value = ContoStatement.FIND_BY_ID_UTENTE)
	boolean existsContoAttivoByIdUtente(Long idUtente);
	
	Optional<Conto> findByIdProfilo(Long idProfilo);

	Optional<Conto> findByIban(String iban);

	@Modifying
	@Query(value = ContoStatement.INCREMENTA_SALDO)
	int incrementaSaldo(Long idConto, BigDecimal importo);

	@Modifying
	@Query(value = ContoStatement.DECREMENTA_SALDO)
	int decrementaSaldo(Long idConto, BigDecimal importo);

	boolean existsByNumeroRapporto(String numeroRapporto);
	
	@Query(value = ContoStatement.DECREMENTA_SALDO)
	DashBoardBO findDashboard(Long idConto);

}
