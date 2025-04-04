package com.sm.be.smartbank.mapper.bo.operazione;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sm.be.smartbank.model.bo.BonificoUscitaInputData;
import com.sm.be.smartbank.model.bo.DepositoInputData;
import com.sm.be.smartbank.model.bo.ElencoOperazioniBO;
import com.sm.be.smartbank.model.bo.PagamentoInputData;
import com.sm.be.smartbank.model.bo.PrelievoInputData;
import com.sm.be.smartbank.model.bo.filter.GetElencoOperazioniFilter;
import com.sm.be.smartbank.model.dto.OperazioneDTO;
import com.sm.be.smartbank.model.dto.operazione.getelencooperazioni.GetElencoOperazioniRequest;
import com.sm.be.smartbank.model.dto.operazione.getelencooperazioni.GetElencoOperazioniResponse;
import com.sm.be.smartbank.model.dto.operazione.getoperazione.GetOperazioneResponse;
import com.sm.be.smartbank.model.dto.operazione.setinsertbonificoinuscita.SetInsertBonificoInUscitaRequest;
import com.sm.be.smartbank.model.dto.operazione.setinsertbonificoinuscita.SetInsertBonificoInUscitaResponse;
import com.sm.be.smartbank.model.dto.operazione.setinsertdeposito.SetInsertDepositoRequest;
import com.sm.be.smartbank.model.dto.operazione.setinsertpagamento.SetInsertPagamentoRequest;
import com.sm.be.smartbank.model.dto.operazione.setinsertprelievo.SetInsertPrelievoRequest;
import com.sm.be.smartbank.model.entity.operazione.Bonifico;

@Mapper
public interface OperazioneMapper {

	BonificoUscitaInputData toBonificoUscitaInputData(SetInsertBonificoInUscitaRequest request);

	@Mapping(source = "nome", target = "beneficiario")
	@Mapping(source = "operazione.importo", target = "importo")
	SetInsertBonificoInUscitaResponse toSetInsertBonificoInUscitaResponse(Bonifico bonifico);

	PagamentoInputData toPagamentoInputData(SetInsertPagamentoRequest request);

	PrelievoInputData toPrelievoInputData(SetInsertPrelievoRequest request);

	DepositoInputData toDepositoInputData(SetInsertDepositoRequest request);

	GetElencoOperazioniFilter toElencoOperazioniFilter(GetElencoOperazioniRequest request);

	List<GetElencoOperazioniResponse> toGetElencoOperazioniResponse(List<ElencoOperazioniBO> bo);
	
	GetOperazioneResponse toGetOperazioneResponse(OperazioneDTO dto);

}
