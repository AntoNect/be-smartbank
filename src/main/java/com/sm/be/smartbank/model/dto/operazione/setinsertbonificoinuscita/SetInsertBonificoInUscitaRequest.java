package com.sm.be.smartbank.model.dto.operazione.setinsertbonificoinuscita;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetInsertBonificoInUscitaRequest {

	@NotBlank(message = "Il nome del beneficiario è obbligatorio")
	private String nomeBeneficiario;

	@NotBlank(message = "L'iban del beneficiario è obbligatorio")
	private String ibanBeneficiario;

	@NotNull(message = "L'importo è obbligatorio")
	@Min(value = 1, message = "L'importo minimo è di un euro")
	private BigDecimal importo;

	@NotBlank(message = "La causale è obbligatoria")
	private String causale;

	private boolean istantaneo;

	private LocalDate dataAddebito;

}
