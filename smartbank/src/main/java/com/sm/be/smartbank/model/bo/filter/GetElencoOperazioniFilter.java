package com.sm.be.smartbank.model.bo.filter;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetElencoOperazioniFilter {

	private int codTipoOperazione;

	private LocalDate dataInizio;

	private LocalDate dataFine;

}
