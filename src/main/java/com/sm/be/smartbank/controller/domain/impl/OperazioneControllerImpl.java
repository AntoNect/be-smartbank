package com.sm.be.smartbank.controller.domain.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.be.smartbank.controller.domain.OperazioneController;
import com.sm.be.smartbank.mapper.bo.operazione.OperazioneMapper;
import com.sm.be.smartbank.model.bo.ElencoOperazioniBO;
import com.sm.be.smartbank.model.dto.OperazioneDTO;
import com.sm.be.smartbank.model.dto.operazione.getelencooperazioni.GetElencoOperazioniRequest;
import com.sm.be.smartbank.model.dto.operazione.getelencooperazioni.GetElencoOperazioniResponse;
import com.sm.be.smartbank.model.dto.operazione.getoperazione.GetOperazioneResponse;
import com.sm.be.smartbank.model.dto.operazione.setinsertbonificoinuscita.SetInsertBonificoInUscitaRequest;
import com.sm.be.smartbank.model.dto.operazione.setinsertbonificoinuscita.SetInsertBonificoInUscitaResponse;
import com.sm.be.smartbank.model.dto.operazione.setinsertdeposito.SetInsertDepositoRequest;
import com.sm.be.smartbank.model.dto.operazione.setinsertdeposito.SetInsertDepositoResponse;
import com.sm.be.smartbank.model.dto.operazione.setinsertpagamento.SetInsertPagamentoRequest;
import com.sm.be.smartbank.model.dto.operazione.setinsertpagamento.SetInsertPagamentoResponse;
import com.sm.be.smartbank.model.dto.operazione.setinsertprelievo.SetInsertPrelievoRequest;
import com.sm.be.smartbank.model.dto.operazione.setinsertprelievo.SetInsertPrelievoResponse;
import com.sm.be.smartbank.model.entity.operazione.Bonifico;
import com.sm.be.smartbank.service.domain.aggr.AggrOperazioneService;
import com.sm.be.smartbank.service.domain.elem.OperazioneService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/operazione")
public class OperazioneControllerImpl implements OperazioneController {

	private OperazioneService operazioneService;
	private AggrOperazioneService aggrOperazioneService;

	@Override
	@PostMapping("/bonifico-in-uscita/insert")
	public ResponseEntity<SetInsertBonificoInUscitaResponse> setInsertBonifico(
			@Valid @RequestBody SetInsertBonificoInUscitaRequest request) {

		OperazioneMapper mapper = Mappers.getMapper(OperazioneMapper.class);
		Bonifico bonifico = aggrOperazioneService
				.setInsertOperazioneBonificoInUscita(mapper.toBonificoUscitaInputData(request));
		return ResponseEntity.ok(mapper.toSetInsertBonificoInUscitaResponse(bonifico));
	}

	@Override
	@PostMapping("/terze-parti/pagamento/insert")
	public ResponseEntity<SetInsertPagamentoResponse> setInsertPagamento(
			@Valid @RequestBody SetInsertPagamentoRequest request) {

		OperazioneMapper mapper = Mappers.getMapper(OperazioneMapper.class);
		Long idOperazione = aggrOperazioneService.setInsertOperazionePagamento(mapper.toPagamentoInputData(request));
		return ResponseEntity.ok(SetInsertPagamentoResponse.builder().idOperazione(idOperazione).build());
	}

	@Override
	@PostMapping("/terze-parti/prelievo/insert")
	public ResponseEntity<SetInsertPrelievoResponse> setInsertPrelievo(
			@Valid @RequestBody SetInsertPrelievoRequest request) {

		OperazioneMapper mapper = Mappers.getMapper(OperazioneMapper.class);
		Long idOperazione = aggrOperazioneService.setInsertOperazionePrelievo(mapper.toPrelievoInputData(request));
		return ResponseEntity.ok(SetInsertPrelievoResponse.builder().idOperazione(idOperazione).build());
	}

	@Override
	@PostMapping("/terze-parti/deposito/insert")
	public ResponseEntity<SetInsertDepositoResponse> setInsertDeposito(
			@Valid @RequestBody SetInsertDepositoRequest request) {

		OperazioneMapper mapper = Mappers.getMapper(OperazioneMapper.class);
		Long idOperazione = aggrOperazioneService.setInsertOperazioneDeposito(mapper.toDepositoInputData(request));
		return ResponseEntity.ok(SetInsertDepositoResponse.builder().idOperazione(idOperazione).build());
	}

	@Override
	@GetMapping("/{idOperazione}")
	public ResponseEntity<GetOperazioneResponse> getOperazione(@PathVariable Long idOperazione) {

		OperazioneMapper mapper = Mappers.getMapper(OperazioneMapper.class);
		OperazioneDTO dto = operazioneService.getOperazione(idOperazione);
		return ResponseEntity.ok(mapper.toGetOperazioneResponse(dto));
	}

	@Override
	@PostMapping("/search")
	public ResponseEntity<Slice<GetElencoOperazioniResponse>> getElencoOperazioni(
			@Valid @RequestBody GetElencoOperazioniRequest request, Pageable pageable) {

		OperazioneMapper mapper = Mappers.getMapper(OperazioneMapper.class);
		Slice<ElencoOperazioniBO> elenco = operazioneService
				.getElencoOperazioni(mapper.toElencoOperazioniFilter(request), pageable);
		return ResponseEntity.ok(
				new SliceImpl<>(mapper.toGetElencoOperazioniResponse(elenco.getContent()), pageable, elenco.hasNext()));
	}

}
