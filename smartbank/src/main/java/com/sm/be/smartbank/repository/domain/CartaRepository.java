package com.sm.be.smartbank.repository.domain;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sm.be.smartbank.model.entity.carta.Carta;
import com.sm.be.smartbank.repository.domain.statement.CartaStatement;

public interface CartaRepository extends JpaRepository<Carta, Long> {

	Slice<Carta> findByIdContoIsNullAndDataInizioIsNull(Pageable pageable);

	@Query(value = CartaStatement.FIND_BY_NUMERO_AND_MESE_AND_ANNO_AND_CVV)
	Optional<Carta> findByNumeroAndMeseAndAnnoAndCvv(String numero, String mese, String anno, String cvv);

	Optional<Carta> findByIdContoAndDataInizioIsNotNullAndDataFineIsNull(Long idConto);

}
