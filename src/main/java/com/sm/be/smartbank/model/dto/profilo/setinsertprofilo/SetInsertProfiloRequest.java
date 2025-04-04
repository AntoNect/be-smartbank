package com.sm.be.smartbank.model.dto.profilo.setinsertprofilo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetInsertProfiloRequest {

	@NotNull
	private String nome;

	@NotNull
	private String cognome;

	@NotNull
	private String codiceFiscale;

	@NotNull
	private String telefono;

	@NotNull
	private String indirizzo;

	@NotNull
	private Long idCittaResidenza;

	@NotNull
	private LocalDate dataNascita;

}
