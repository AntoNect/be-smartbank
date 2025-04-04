package com.sm.be.smartbank.model.dto.operazione.getelencooperazioni;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetElencoOperazioniRequest {

	private Integer codTipoOperazione;

	private LocalDate dataInizio;

	private LocalDate dataFine;

}
