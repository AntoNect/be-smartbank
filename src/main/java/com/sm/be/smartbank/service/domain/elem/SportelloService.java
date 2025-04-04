package com.sm.be.smartbank.service.domain.elem;

import java.math.BigDecimal;
import java.util.Optional;

import com.sm.be.smartbank.model.entity.atm.Atm;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.model.entity.operazione.Sportello;

public interface SportelloService {

	Sportello setInsertSportello(Operazione operazione, Atm atm);

	BigDecimal getSumImportoPrelieviMeseCorrente(Long idConto, String codMese, String anno);

	Optional<Sportello> getById(Long idOperazione);

}
