package com.sm.be.smartbank.model.dto.profilo.setinsertprofilo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetInsertProfiloRequest {

	@NotBlank(message = "Il nome è obbligatorio")
	private String nome;

	@NotBlank(message = "Il cognome è obbligatorio")
	private String cognome;

	@NotBlank(message = "Il codice fiscale è obbligatorio")
	private String codiceFiscale;

	@NotBlank(message = "Il numero di telefono è obbligatorio")
	@Pattern(regexp = "^\\+?\\d{9,15}$", message = "Il numero di telefono non è valido")
	private String telefono;

	@NotBlank(message = "L'indirizzo è obbligatorio")
	private String indirizzo;

	@NotNull
	private Long idCittaResidenza;

	@NotNull
	private LocalDate dataNascita;

}
