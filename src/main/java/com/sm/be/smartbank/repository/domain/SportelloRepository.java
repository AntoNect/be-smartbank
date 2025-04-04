package com.sm.be.smartbank.repository.domain;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sm.be.smartbank.model.entity.operazione.Sportello;
import com.sm.be.smartbank.repository.domain.statement.SportelloStatement;

public interface SportelloRepository extends JpaRepository<Sportello, Long> {

	@Query(value = SportelloStatement.SUM_IMPORTO_PRELIVEVI_MESE_CORRENTE)
	BigDecimal sumImportoPrelieviMeseCorrente(Long idConto, String mese, String anno);

}
