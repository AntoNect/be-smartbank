package com.sm.be.smartbank.service.domain.elem;

import java.math.BigDecimal;
import java.util.Optional;

import com.sm.be.smartbank.model.bo.DashBoardBO;
import com.sm.be.smartbank.model.entity.personale.Conto;
import com.sm.be.smartbank.model.entity.personale.Profilo;

public interface ContoService {

	Conto setInsertConto(Profilo profilo);

	Optional<Conto> getContoAttivo();
	
	boolean isContoAttivoByIdUtente(Long idUtente);

	Optional<Conto> getContoByIban(String iban);

	int incrementaSaldo(Long idConto, BigDecimal importo);

	int decrementaSaldo(Long idConto, BigDecimal importo);

	DashBoardBO getDashboard();

}
