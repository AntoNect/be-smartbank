package com.sm.be.smartbank.service.domain.aggr;

import com.sm.be.smartbank.model.bo.ProfiloInputData;
import com.sm.be.smartbank.model.entity.personale.Profilo;

public interface AggrProfiloService {

	Profilo setInsertProfilo(ProfiloInputData inputData);

}
