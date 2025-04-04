package com.sm.be.smartbank.model.dto.profilo.getprofilo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetProfiloResponse {

	private String nome;

	private String cognome;

	private String codiceFiscale;

	private String telefono;

	private String indirizzoResidenza;

	private String cittaResidenza;
	
	private LocalDate dataNascita;

}
