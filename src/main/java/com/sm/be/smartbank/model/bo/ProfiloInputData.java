package com.sm.be.smartbank.model.bo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfiloInputData {

	private String nome;

	private String cognome;

	private String codiceFiscale;

	private String telefono;

	private String indirizzoResidenza;

	private Long idCittaResidenza;

	private LocalDate dataNascita;

}
