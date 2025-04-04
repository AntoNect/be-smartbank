package com.sm.be.smartbank.service.domain.elem;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.sm.be.smartbank.model.bo.ElencoOperazioniBO;
import com.sm.be.smartbank.model.bo.OperazioneInputData;
import com.sm.be.smartbank.model.bo.filter.GetElencoOperazioniFilter;
import com.sm.be.smartbank.model.dto.OperazioneDTO;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.model.entity.personale.Conto;

public interface OperazioneService {

	Operazione setInsertOperazione(Conto conto, OperazioneInputData inputData);

	Slice<ElencoOperazioniBO> getElencoOperazioni(GetElencoOperazioniFilter filter, Pageable pageable);

	BigDecimal getImportoEntrateNelPerioByIdConto(Long idConto, LocalDate dataInizio, LocalDate dataFine);

	BigDecimal getImportoUsciteNelPerioByIdConto(Long idConto, LocalDate dataInizio, LocalDate dataFine);

	OperazioneDTO getOperazione(Long idOperazione);

}
