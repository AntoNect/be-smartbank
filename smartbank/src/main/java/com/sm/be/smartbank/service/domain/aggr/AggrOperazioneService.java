package com.sm.be.smartbank.service.domain.aggr;

import com.sm.be.smartbank.model.bo.BonificoUscitaInputData;
import com.sm.be.smartbank.model.bo.DepositoInputData;
import com.sm.be.smartbank.model.bo.PagamentoInputData;
import com.sm.be.smartbank.model.bo.PrelievoInputData;
import com.sm.be.smartbank.model.entity.operazione.Bonifico;

public interface AggrOperazioneService {

	Bonifico setInsertOperazioneBonificoInUscita(BonificoUscitaInputData inputData);

	Long setInsertOperazionePagamento(PagamentoInputData inputData);

	Long setInsertOperazionePrelievo(PrelievoInputData inputData);

	Long setInsertOperazioneDeposito(DepositoInputData inputData);

}
