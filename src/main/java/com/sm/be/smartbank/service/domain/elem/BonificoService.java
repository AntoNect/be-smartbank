package com.sm.be.smartbank.service.domain.elem;

import java.util.Optional;

import com.sm.be.smartbank.model.bo.BonificoIngressoInputData;
import com.sm.be.smartbank.model.bo.BonificoUscitaInputData;
import com.sm.be.smartbank.model.entity.operazione.Bonifico;
import com.sm.be.smartbank.model.entity.operazione.Operazione;

public interface BonificoService {

	Bonifico setInsertBonificoInUscita(Operazione operazione, BonificoUscitaInputData inputData);

	Bonifico setInsertBonificoInIngresso(Operazione operazione, BonificoIngressoInputData inputData);

	Optional<Bonifico> getById(Long idOperazione);

}
