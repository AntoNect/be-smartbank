package com.sm.be.smartbank.controller.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Operazione")
public interface OperazioneController {

	@Operation(summary = "Inserisce l'operazione di bonifico in uscita")
	public ResponseEntity<SetInsertBonificoInUscitaResponse> setInsertBonifico(
			@Valid SetInsertBonificoInUscitaRequest request);

	@Operation(summary = "Inserisce l'operazione di un pagamento")
	public ResponseEntity<SetInsertPagamentoResponse> setInsertPagamento(@Valid SetInsertPagamentoRequest request);

	@Operation(summary = "Inserisce l'operazione di un prelievo")
	public ResponseEntity<SetInsertPrelievoResponse> setInsertPrelievo(@Valid SetInsertPrelievoRequest request);

	@Operation(summary = "Inserisce l'operazione di un deposito")
	public ResponseEntity<SetInsertDepositoResponse> setInsertDeposito(@Valid SetInsertDepositoRequest request);

	@Operation(summary = "Restituisce il dettaglio di un'operazione")
	public ResponseEntity<GetOperazioneResponse> getOperazione(@PathVariable Long id);

	@Operation(summary = "Restituisce l'elenco delle operazioni")
	public ResponseEntity<Slice<GetElencoOperazioniResponse>> getElencoOperazioni(
			@Valid GetElencoOperazioniRequest request, Pageable pageable);

}
