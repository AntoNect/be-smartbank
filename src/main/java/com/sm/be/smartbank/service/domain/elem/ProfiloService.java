package com.sm.be.smartbank.service.domain.elem;

import com.sm.be.smartbank.model.bo.ProfiloInputData;
import com.sm.be.smartbank.model.entity.personale.Profilo;
import com.sm.be.smartbank.model.entity.personale.Utente;

public interface ProfiloService {

	Profilo getProfilo();

	Profilo setInsertProfilo(Utente utente, ProfiloInputData profilo);

	Profilo setUpdateProfilo(ProfiloInputData inputData);

}
