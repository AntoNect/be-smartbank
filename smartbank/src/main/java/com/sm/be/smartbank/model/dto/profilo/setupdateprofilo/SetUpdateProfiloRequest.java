package com.sm.be.smartbank.model.dto.profilo.setupdateprofilo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetUpdateProfiloRequest {

	@NotNull
	private String telefono;

	@NotNull(message = "L'indirizzo di residenza è obbligatorio")
	@NotBlank(message = "L'indirizzo di residenza è obbligatorio")
	private String indirizzoResidenza;

	@NotNull
	private Long idCittaResidenza;

	@NotNull
	private LocalDate dataNascita;

}
