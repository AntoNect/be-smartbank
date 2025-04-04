package com.sm.be.smartbank.model.dto.profilo.setinsertprofilo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetInsertProfiloResponse {

	private String nome;

	private String cognome;

	private String codiceFiscale;

	private String telefono;

	private String indirizzo;

	private String cittaResidenza;
	
	private LocalDate dataNascita;
	
}
