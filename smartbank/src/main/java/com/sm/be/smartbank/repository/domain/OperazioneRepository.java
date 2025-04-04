package com.sm.be.smartbank.repository.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sm.be.smartbank.model.bo.filter.GetElencoOperazioniFilter;
import com.sm.be.smartbank.model.bo.view.ElencoOperazioniView;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.repository.domain.statement.OperazioneStatement;

public interface OperazioneRepository extends JpaRepository<Operazione, Long> {

	@Query(value = OperazioneStatement.FIND_ELENCO_OPERAZIONI, nativeQuery = true)
	Slice<ElencoOperazioniView> findElencoOperazioni(Long idConto, GetElencoOperazioniFilter filter, Pageable pageable);

	@Query(value = OperazioneStatement.SUM_IMPORTO_ENTRATE_NEL_PERIODO_BY_ID_CONTO)
	BigDecimal sumImportoEntrateNelPerioByIdConto(Long idConto, LocalDate dataInizio, LocalDate dataFine);
	
	@Query(value = OperazioneStatement.SUM_IMPORTO_USCITE_NEL_PERIODO_BY_ID_CONTO)
	BigDecimal sumImportoUsciteNelPerioByIdConto(Long idConto, LocalDate dataInizio, LocalDate dataFine);

}
